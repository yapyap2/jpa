import Domain.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

public class main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");

        EntityManager em = emf.createEntityManager();
        em.setFlushMode(FlushModeType.AUTO);
        EntityTransaction tx = em.getTransaction();


        try{
            tx.begin();

            Member member = new Member();
            member.setName("yapyap");
            em.persist(member);

            Delivery delivery = new Delivery();
            delivery.setCity("chuncheon");
            em.persist(delivery);

            Order order = new Order();
            order.setStatus(OrderStatus.ORDER);
            order.setMember(member);
            order.setDelivery(delivery);
            em.persist(order);

            Item item = new Item();
            item.setName("macbook");
            item.setPrice(10000);

            Category category = new Category();
            category.setName("IT");
            em.persist(category);

            item.addCategory(category);

            em.persist(item);

            em.persist(order);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setOrder(order);

            em.persist(orderItem);




            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.clear();

            Member member = em.find(Member.class, 1L);

            Order order = member.getOrders().get(0);

            Delivery delivery = order.getDelivery();

            OrderItem orderItem = order.getOrderItems().get(0);

            Item item = orderItem.getItem();

            Category category = item.getCategories().get(0);




            em.close();
            emf.close();
        }
    }


}
