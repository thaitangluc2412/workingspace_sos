package com.cnpm.workingspace.service;

import com.cnpm.workingspace.dao.ReservationDao;
import com.cnpm.workingspace.dto.PropertyDto;
import com.cnpm.workingspace.dto.ReservationDto;
import com.cnpm.workingspace.dto.RoomDto;
import com.cnpm.workingspace.dto.V2ReservationDto;
import com.cnpm.workingspace.model.*;
import com.cnpm.workingspace.repository.*;
import com.cnpm.workingspace.sdo.Budget;
import com.cnpm.workingspace.sdo.DateStatus;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;

    private RoomRepository roomRepository;

    private ReservationDao reservationDao;

    private CustomerRepository customerRepository;

    private ReservationStatusService reservationStatusService;

    private ReservationStatusRepository reservationStatusRepository;

    private ModelMapper toDtoMapper;

    private ModelMapper toEntityMapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository, RoomRepository roomRepository, ReservationDao reservationDao, CustomerRepository customerRepository, ReservationStatusService reservationStatusService, ReservationStatusRepository reservationStatusRepository, ModelMapper toDtoMapper, ModelMapper toEntityMapper) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.reservationDao = reservationDao;
        this.customerRepository = customerRepository;
        this.reservationStatusService = reservationStatusService;
        this.reservationStatusRepository = reservationStatusRepository;
        this.toDtoMapper = toDtoMapper;
        this.toEntityMapper = toEntityMapper;

        System.out.println("call first");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.toDtoMapper = toDtoMapper;
        Converter<Date, String> dateToString = context -> {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").format(context.getSource());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        };
        this.toDtoMapper.createTypeMap(Reservation.class, ReservationDto.class)
                .addMappings(mapper -> mapper.map(src -> src.getCustomer().getCustomerId(), ReservationDto::setCustomerId))
                .addMappings(mapper -> mapper.map(src -> src.getRoom().getRoomId(), ReservationDto::setRoomId))
                .addMappings(mapper -> mapper.using(dateToString).map(Reservation::getCreateDate, ReservationDto::setCreateDate))
                .addMappings(mapper -> mapper.using(dateToString).map(Reservation::getStartDate, ReservationDto::setStartDate))
                .addMappings(mapper -> mapper.using(dateToString).map(Reservation::getEndDate, ReservationDto::setEndDate))
                .addMappings(mapper -> mapper.map(src -> src.getReservationId(), ReservationDto::setReservationId))
                .addMappings(mapper -> mapper.map(src -> src.getReservationStatus().getReservationStatusId(), ReservationDto::setReservationStatusId));
    }

    @Autowired
    public void setToEntityMapper(ModelMapper toEntityMapper) {
        Converter<Integer, Customer> customerConverter = context -> customerRepository.getById(context.getSource());
        Converter<Integer, Room> roomConverter = context -> roomRepository.getById(context.getSource());
        Converter<String, Date> dateConverter = context -> {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(context.getSource());
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        };
        Converter<Integer, ReservationStatus> reservationStatusConverter = context -> reservationStatusRepository.getById(context.getSource());
        this.toEntityMapper = toEntityMapper;
        this.toEntityMapper.createTypeMap(ReservationDto.class, Reservation.class)
                .addMappings(mapper -> mapper.using(customerConverter).map(ReservationDto::getCustomerId, Reservation::setCustomer))
                .addMappings(mapper -> mapper.using(roomConverter).map(ReservationDto::getRoomId, Reservation::setRoom))
                .addMappings(mapper -> mapper.using(dateConverter).map(ReservationDto::getCreateDate, Reservation::setCreateDate))
                .addMappings(mapper -> mapper.using(dateConverter).map(ReservationDto::getStartDate, Reservation::setStartDate))
                .addMappings(mapper -> mapper.using(dateConverter).map(ReservationDto::getEndDate, Reservation::setEndDate))
                .addMappings(mapper -> mapper.using(reservationStatusConverter).map(ReservationDto::getReservationStatusId, Reservation::setReservationStatus));

    }

    @Override
    public List<ReservationDto> getAllReservation() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map(reservation -> toDtoMapper.map(reservation, ReservationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void insertReservation(ReservationDto reservationDto) {
        Reservation reservation = toEntityMapper.map(reservationDto, Reservation.class);
        reservationRepository.save(reservation);
    }

    @Override
    public boolean updateReservation(ReservationDto reservationDto, int id) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (reservationOpt.isPresent()) {
            reservationRepository.save(toEntityMapper.map(reservationDto, Reservation.class));
            return true;
        }
        return false;
    }

    @Override
    public void deleteReservation(int id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public ReservationDto getReservationById(int id) {
        Reservation reservation = reservationRepository.getById(id);
        return toDtoMapper.map(reservation, ReservationDto.class);
    }

    @Override
    public List<DateStatus> getDateStatus(int roomId, int month, int year) {
        return reservationDao.getDateStatus(roomId, month, year);
    }

    @Override
    public String getFurthestValidDate(int roomId, Date from) {
        return reservationDao.getFurthestValidDate(roomId, from);
    }

    @Override
    public List<Date> getAllInvalidDate(int roomId) {
        return reservationDao.getAllInvalidDates(roomId);
    }

    @Override
    public boolean updateReservationStatus(int id, int reservationStatusId) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        Optional<ReservationStatus> reservationStatusOpt = reservationStatusRepository.findById(reservationStatusId);
        if (reservationOpt.isPresent() && reservationStatusOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();
            ReservationStatus reservationStatus = reservationStatusOpt.get();
            reservation.setReservationStatus(reservationStatus);
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    @Override
    public List<ReservationDto> getReservationBySellerId(int customerId, int reservationStatus) {
        return reservationDao.getReservationByCustomerId(customerId, reservationStatus);
    }

    @Override
    public List<ReservationDto> getReservationByCustomerId(int customerId) {
        List<Reservation> reservations = reservationRepository.getByCustomerCustomerId(customerId);

        return reservations.stream().map(reservation -> toDtoMapper.map(reservation, ReservationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> getReservationBySellerId(int sellerId) {
        List<ReservationDto> reservations = reservationDao.getReservationBySellerId(sellerId);

        return reservations;
    }

    @Override
    public Budget getBudget() {
        Budget budget = reservationDao.getBudget();
        return budget;
    }

    @Override
    public double totalProfit() {
        return reservationRepository.countTotalProfit();
    }

    @Override
    public List<V2ReservationDto> getReservationBySellerIdV2(int sellerId) {
        return reservationDao.getReservationBySerller(sellerId);
    }

    @Override
    public List<V2ReservationDto> getReservationByCustomerIdV2(int customerId) {
        return reservationDao.getReservationByCustomer(customerId);
    }

    @Override
    public List<Integer> getReservationAmountPerMonth() {
        return reservationDao.getReservationAmountPerMonth();
    }
}
