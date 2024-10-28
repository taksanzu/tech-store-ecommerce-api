package org.tak.techstoreecommerce.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tak.techstoreecommerce.dto.OrderDTO;
import org.tak.techstoreecommerce.model.Cart;
import org.tak.techstoreecommerce.model.CartItem;
import org.tak.techstoreecommerce.model.Order;
import org.tak.techstoreecommerce.model.OrderItem;
import org.tak.techstoreecommerce.repository.CartRepository;
import org.tak.techstoreecommerce.repository.OrderItemRepository;
import org.tak.techstoreecommerce.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDTO createOrder(String email) {
        Cart cart = cartRepository.findCartByEmail(email);
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus("PENDING");

        order = orderRepository.save(order);

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getProductPrice());
            orderItemRepository.save(orderItem);
        }
        cartRepository.delete(cart);

        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        return orderDTO;
    }
}
