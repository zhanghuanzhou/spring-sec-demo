import my.demo.dao.CustomerDAO;
import my.demo.dao.OrderDAO;
import my.demo.dao.UserDAO;
import my.demo.entity.Customer;
import my.demo.entity.Order;
import my.demo.entity.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * Created by zhzho on 2017/6/21.
 */
public class UserAssignServiceTest extends BaseJunit4Test {

    final Logger logger = LoggerFactory.getLogger(UserAssignServiceTest.class);

    @Resource  //自动注入,默认按名称
    private UserDAO userDAO;

    @Resource
    private OrderDAO orderDAO;

    @Resource
    private CustomerDAO customerDAO;


    @Test   //标明是测试方法
    @Transactional()   //标明此方法需使用事务
    @Rollback(false)
    public void insert() {
        /*User user = new User();
        user.setEmail("@test");
        userDAO.save(user);
        if(logger.isDebugEnabled()){
            logger.debug(user.toString());
        }*/

        /*Customer customer = new Customer();
        customer.setCustomerName("c@1");*/
        // Customer customer = customerDAO.getById(11l);
        //Order order = orderDAO.getById(9l);
        //order.setRemark("@1");
        //order.setCustomer(customer);
        //customer.getOrders().add(order);
        //customer.setCustomerName("xx");
        //customerDAO.save(customer);
        //orderDAO.save(order);
        //logger.info(order.toString());
        //customerDAO.delete(customer);
        //customer.getOrders().clear();
        //customerDAO.save(customer);
        //order.setCustomer(null);

        Customer customer = new Customer();
        customer.setCustomerId(18l);
        customer.setCustomerName("c@18");
        Order order = new Order();
        customer.getOrders().add(order);
        order.setOrderId(17l);
        //order.setCustomer(customer);
        customerDAO.saveOrUpdate(customer);
    }

    @Test   //标明是测试方法
    @Transactional()   //标明此方法需使用事务
    @Rollback(false)
    public void testUpdate() {
        Order order = new Order();
        order.setOrderId(17l);
        //orderDAO.getById(17l);
        orderDAO.update(order);
    }
}
