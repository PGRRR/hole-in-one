package nosleepcoders.holeinone.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 매장 도메인 객체
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "stores")
public class Store {

   @Id
   @Column(nullable = false)
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long store_id;

   @Column(nullable = false)
   private String store_name;

   @Column(nullable = false)
   private String store_address;

   @Column(nullable = false)
   private Long store_phone;
}

