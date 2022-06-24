package com.cnpm.workingspace.dao;

import com.cnpm.workingspace.dto.ReservationDto;
import com.cnpm.workingspace.dto.V2ReservationDto;
import com.cnpm.workingspace.mapper.DateMapper;
import com.cnpm.workingspace.mapper.DateStatusMapper;
import com.cnpm.workingspace.mapper.ReservationDtoMapper;
import com.cnpm.workingspace.model.Price;
import com.cnpm.workingspace.sdo.Budget;
import com.cnpm.workingspace.sdo.DateStatus;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.transform.Transformer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcReservationDao implements ReservationDao {
    private final Logger LOGGER = LoggerFactory.getLogger(JdbcReservationDao.class);

    private JdbcTemplate jdbcTemplateObject;
    private EntityManager entityManager;

    @Autowired
    public JdbcReservationDao(JdbcTemplate jdbcTemplateObject, EntityManager entityManager) {
        this.entityManager = entityManager;
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

    @Override
    public List<DateStatus> getDateStatus(int roomId, int month, int year) {
        List<DateStatus> ret = new ArrayList<>();
        final String sql = "WITH RECURSIVE days AS\n" +
                "(\n" +
                "   SELECT 1 AS day UNION ALL SELECT day + 1 FROM days WHERE day < DAY(LAST_DAY('" + year + "-" + month + "-01'))\n" +
                ")\n" +
                "\n" +
                "SELECT d.day, IF(d.day >= DAY(r.start_date) AND d.day <= DAY(r.end_date),TRUE,FALSE) AS status FROM days AS d\n" +
                "LEFT JOIN reservation AS r\n" +
                "ON (\n" +
                "    d.day >= DAY(r.start_date) AND d.day <= DAY(r.end_date)\n" +
                "    AND r.reservation_status_id = 1\n" +
                "    AND MONTH(r.start_date) = " + month + "\n" +
                "    AND YEAR(r.start_date) = " + year + "\n" +
                "    AND r.room_id = " + roomId + "\n" +
                ")\n" +
                "ORDER BY day\n";
        ret.addAll(jdbcTemplateObject.query(sql, new DateStatusMapper()));
        return ret;
    }

    @Override
    public String getFurthestValidDate(int roomId, Date fromDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String from = format.format(fromDate);
        final String sql = "SELECT DATE(start_date) - INTERVAL 1 DAY AS furthest_date FROM reservation \n" +
                "WHERE room_id = " + roomId + "\n" +
                "AND '" + from + "' < DATE(start_date)\n" +
                "AND reservation_status_id != 4\n" +
                "ORDER BY DATE(start_date)\n" +
                "LIMIT 1\n";
        System.out.println("###### sql : " + sql);
        String ret = "2069-12-31";
        try {
            ret = jdbcTemplateObject.queryForObject(sql, String.class);
        } catch (Exception e) {
            System.out.println("dont have furthest valid date");
        }
        return ret;
    }

    @Override
    public List<Date> getAllInvalidDates(int roomId) {
        List<Date> ret = new ArrayList<>();
        final String sql = "SELECT DISTINCT gen_date AS date FROM \n" +
                "(SELECT ADDDATE('1970-01-01',t4*10000 + t3*1000 + t2*100 + t1*10 + t0) gen_date FROM\n" +
                " (SELECT 0 t0 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t0,\n" +
                " (SELECT 0 t1 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t1,\n" +
                " (SELECT 0 t2 union SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t2,\n" +
                " (SELECT 0 t3 union SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t3,\n" +
                " (SELECT 0 t4 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t4) v\n" +
                "JOIN reservation ON \n" +
                "gen_date >= DATE(start_date) AND gen_date <= DATE(end_date)\n" +
                "AND room_id = " + roomId + "\n" +
                "AND reservation_status_id != 4\n" +
                "ORDER BY gen_date";
        try {
            ret.addAll(jdbcTemplateObject.query(sql, new DateMapper()));
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }
        return ret;
    }

    @Override
    public List<ReservationDto> getReservationByCustomerId(int customerId, int reservationStatus) {
        List<ReservationDto> ret = new ArrayList<>();
        final String sql = "SELECT re.* FROM reservation AS re\n" +
                "JOIN room AS ro\n" +
                "ON re.room_id = ro.room_id\n" +
                "JOIN property AS p\n" +
                "ON p.property_id = ro.property_id\n" +
                "WHERE re.reservation_status_id = " + reservationStatus + "\n" +
                "AND p.customer_id = " + customerId;

        System.out.println("sql : " + sql);

        try {
            ret.addAll(jdbcTemplateObject.query(sql, new ReservationDtoMapper()));
            System.out.println("added");
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }
        return ret;
    }

    @Override
    public List<ReservationDto> getReservationBySellerId(int sellerId) {
        List<ReservationDto> ret = new ArrayList<>();
        final String sql = "SELECT re.* FROM reservation AS re\n" +
                "JOIN room AS ro\n" +
                "ON re.room_id = ro.room_id\n" +
                "JOIN property AS p\n" +
                "ON p.property_id = ro.property_id\n" +
                "WHERE p.customer_id = " + sellerId;
        try {
            ret.addAll(jdbcTemplateObject.query(sql, new ReservationDtoMapper()));
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }
        return ret;
    }

    @Override
    public Budget getBudget() {
        Budget ret = new Budget();
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        int preMonth = month - 1;
        int preYear = year;
        if (month == 1) {
            preMonth = 12;
            preYear = year - 1;
        }
        final String sql = "WITH oldMonth as (\n" +
                "select IFNULL(sum(total), 1) as oldTotal\n" +
                "from reservation \n" +
                "where reservation_status_id = 2 and (MONTH(create_date) = " + preMonth + ") and (YEAR(create_date) = " + preYear + ") \n" +
                ") \n" +
                "select ROUND(IFNULL(sum(reservation.total), 1) / oldMonth.oldTotal * 100 - 100, 2) as percent, sum(reservation.total) as budget\n" +
                "from reservation, oldMonth\n" +
                "where reservation_status_id = 2 and (MONTH(create_date) = " + month + ") and (YEAR(create_date) = " + year + ") ;";
        try {
            ret = jdbcTemplateObject.queryForObject(sql, (rs, rowNum) -> {
                return new Budget(
                        rs.getDouble("percent"),
                        rs.getDouble("budget")
                );
            });
            System.out.println("added");
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }
        return ret;
    }

    @Override
    public List<V2ReservationDto> getReservationBySerller(int sellerId) {
        List<V2ReservationDto> v2ReservationDtoList = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        final String GET_RESERVATION0_BY_SELLER = "select re.reservation_id reservationId, re.create_date createDate, re.start_date startDate, re.end_date endDate, re.total total, r.room_name roomName, rs.reservation_status_name status, im.url image, re.customer_id customerId\n" +
                "from reservation re\n" +
                "join room r\n" +
                "on re.room_id = r.room_id\n" +
                "join reservation_status rs\n" +
                "on re.reservation_status_id = rs.reservation_status_id\n" +
                "join image_storage ist\n" +
                "on ist.image_storage_id = r.image_storage_id\n" +
                "join image im\n" +
                "on ist.image_storage_id = im.image_storage_id\n" +
                "join property pr\n" +
                "on pr.property_id = r.property_id\n" +
                "where pr.customer_id = " + sellerId;
        return session.createNativeQuery(GET_RESERVATION0_BY_SELLER)
                .addScalar("reservationId", IntegerType.INSTANCE)
                .addScalar("createDate", StringType.INSTANCE)
                .addScalar("startDate", StringType.INSTANCE)
                .addScalar("endDate", StringType.INSTANCE)
                .addScalar("total", DoubleType.INSTANCE)
                .addScalar("status", StringType.INSTANCE)
                .addScalar("image", StringType.INSTANCE)
                .addScalar("roomName", StringType.INSTANCE)
                .addScalar("customerId", IntegerType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(V2ReservationDto.class))
                .getResultList();
    }

    @Override
    public List<V2ReservationDto> getReservationByCustomer(int customerId) {
        List<V2ReservationDto> v2ReservationDtoList = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        final String GET_RESERVATION0_BY_SELLER = "select re.reservation_id reservationId, re.create_date createDate, re.start_date startDate, re.end_date endDate, re.total total, r.room_name roomName, rs.reservation_status_name status, im.url image, pr.customer_id customerId\n" +
                "from reservation re\n" +
                "join room r\n" +
                "on re.room_id = r.room_id\n" +
                "join reservation_status rs\n" +
                "on re.reservation_status_id = rs.reservation_status_id\n" +
                "join image_storage ist\n" +
                "on ist.image_storage_id = r.image_storage_id\n" +
                "join image im\n" +
                "on ist.image_storage_id = im.image_storage_id\n" +
                "join property pr\n" +
                "on pr.property_id = r.property_id\n" +
                "where re.customer_id = " + customerId;
        return session.createNativeQuery(GET_RESERVATION0_BY_SELLER)
                .addScalar("reservationId", IntegerType.INSTANCE)
                .addScalar("createDate", StringType.INSTANCE)
                .addScalar("startDate", StringType.INSTANCE)
                .addScalar("endDate", StringType.INSTANCE)
                .addScalar("total", DoubleType.INSTANCE)
                .addScalar("status", StringType.INSTANCE)
                .addScalar("image", StringType.INSTANCE)
                .addScalar("roomName", StringType.INSTANCE)
                .addScalar("customerId", IntegerType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(V2ReservationDto.class))
                .getResultList();
    }

    @Override
    public List<Integer> getReservationAmountPerMonth() {
        List<Integer> ret=new ArrayList<>();
        final String sql = "SELECT COUNT(*) AS cnt FROM reservation\n" +
                "WHERE YEAR(create_date) = YEAR(NOW())\n" +
                "AND reservation_status_id = " + ReservationStatus.APPROVED.getValue() + "\n" +
                "AND create_date <= NOW()\n" +
                "GROUP BY MONTH(create_date)\n" +
                "ORDER BY MONTH(create_date)";
        try{
            ret.addAll(jdbcTemplateObject.queryForList(sql, Integer.class));
        } catch (Exception e){
            LOGGER.debug("error : {}",e.getMessage());
        }
        return ret;
    }

    public enum ReservationStatus {

        PENDING(1),
        APPROVED(2),
        PAYING(3),
        CANCELLED(4);

        final int value;

        ReservationStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
