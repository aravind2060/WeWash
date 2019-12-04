package com.example.wewash.MyOrders;

public class D_OrdersData {

  public String orderId, name,address,area,phone,dateBooked,expectedPickupTime,noOfClothes,status,key,service;
  public  Integer progress;
  public D_OrdersData(String address, String area, String dateBooked, String expectedPickupTime, String name, String noOfClothes, String orderId, String phone, String status, int progress, String key, String service)
  {
     this.orderId =orderId;
     this.name =name;
     this.address=address;
     this.phone =phone;
     this.area=area;
     this.service = service;
     this.dateBooked=dateBooked;
     this.expectedPickupTime=expectedPickupTime;
     this.noOfClothes=noOfClothes;
     this.status=status;
     this.progress=progress;
     this.key=key;
  }
  public D_OrdersData()
  {

  }


    public String getOrderId() {
        return orderId;
    }


    public String getName() {
        return name;
    }


    public String getArea() {
        return area;
    }



    public String getAddress() {
        return address;
    }


    public String getPhone() {
        return phone;
    }


    public String getDateBooked() {
        return dateBooked;
    }


    public String getExpectedPickupTime() {
        return expectedPickupTime;
    }



    public String getNoOfClothes() {
        return noOfClothes;
    }
    public Integer getProgress() {
        return progress;
    }

    public String getKey() {
        return key;
    }
    public String getService() {
        return service;
    }
    public String getStatus() {
        return status;
    }

}
