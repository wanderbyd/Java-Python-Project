
package com.shoppingmall.orders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shoppingmall.cart.Cart;
import com.shoppingmall.item.Item;
import com.shoppingmall.users.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "orders_seq", allocationSize = 1)
    @Column(name = "orderid")
    private Long orderid;

    @ManyToOne
    @JoinColumn(name = "cartid", referencedColumnName = "cartid", insertable = true, updatable = true)
    @Cascade(value = CascadeType.ALL) 
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "usersid", referencedColumnName = "usersid", insertable = true, updatable = true)
    @Cascade(value = CascadeType.ALL) 
    private Users users;

    @ManyToOne
    @JoinColumn(name = "itemid", referencedColumnName = "itemid", insertable = true, updatable = true)
    @Cascade(value = CascadeType.ALL) 
    private Item item;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "orderprice")
    private Double orderprice;

    
    @Column(name = "orderdate",columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime orderdate;

    public Orders() {
       
    }

    public Orders(Cart cart, Users users, Item item, Integer quantity, Double orderprice) {
        this.cart = cart;
        this.users = users;
        this.item = item;
        this.quantity = quantity;
        this.orderprice = orderprice;
      
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
		this.cart = cart;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Users getUsers() {
        return users;
    }

    public Item getItem() {
        return item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(Double orderprice) {
        this.orderprice = orderprice;
    }

    
    @PrePersist
    protected void onCreate() {
    	 orderdate = LocalDateTime.now().withNano(0);
    }
    
    @Override
    public String toString() {
        return "Orders [orderid=" + orderid + ", cart=" + cart + ", users=" + users + ", item=" + item
                + ", quantity=" + quantity + ", orderprice=" + orderprice + "]";
    }

	public void setUsersid(Users users) {
		
		
	}

	public void setCartid(Cart cart) {
		
		
	}



	public LocalDateTime getOrderdate() {
		return orderdate;
	}


	public void setOrderdate(LocalDateTime orderdate) {
     
        this.orderdate = orderdate.withNano(0);
    }

    public String getFormattedOrderdate() {
      
        return orderdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
    }

	public void setCartid(Long cartid) {
	
		
	}

	public void setUsersid(Long usersid) {
		
		
	}

	public void setCartitem(Map<Item, Long> cartitem) {
		
		
	}


	public void setItems(List<Item> orderItems) {
		
		
	}

	
    public int compareTo(Orders other) {
        return this.getOrderdate().compareTo(other.getOrderdate());
    }


}



