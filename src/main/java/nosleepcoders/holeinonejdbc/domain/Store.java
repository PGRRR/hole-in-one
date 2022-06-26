package nosleepcoders.holeinonejdbc.domain;

/**
 * 매장 도메인 객체
 */
public class Store {
   private Long store_id;
   private String store_name;
   private String store_address;
   private Long store_phone;

   public void setStore_id(Long store_id) {
      this.store_id = store_id;
   }

   public void setStore_name(String store_name) {
      this.store_name = store_name;
   }

   public void setStore_address(String store_address) {
      this.store_address = store_address;
   }

   public void setStore_phone(Long store_phone) {
      this.store_phone = store_phone;
   }

   public Long getStore_id() {
      return store_id;
   }

   public String getStore_name() {
      return store_name;
   }

   public String getStore_address() {
      return store_address;
   }

   public Long getStore_phone() {
      return store_phone;
   }
}

