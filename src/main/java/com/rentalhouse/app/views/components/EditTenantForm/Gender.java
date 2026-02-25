package com.rentalhouse.app.views.components.EditTenantForm;

public class Gender {
   private final int value;
   private final String displayname;

   public Gender(int value, String displayname){
      this.value = value;
      this.displayname = displayname;
   }

   public int getValue(){
      return value;
   }

   public String getDisplayname(){
      return displayname;
   }

   @Override
   public String toString(){
      return displayname;
   }
}
