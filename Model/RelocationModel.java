package com.example.relo.Model;

public class RelocationModel {
    String BedSize;
    int BedNumber;
    String SofaSize;
    int SofasNumber;
    String FridgeSize;
    int FridgeNumber;
    String TableSize;
    int TableNumber;
    String Helper;
    String Date;
    String AdditionalInfo;

    public RelocationModel() {
    }

    public RelocationModel(String bedSize, int bedNumber, String sofaSize, int sofasNumber, String fridgeSize, int fridgeNumber, String tableSize, int tableNumber, String helper, String date, String additionalInfo) {
        BedSize = bedSize;
        BedNumber = bedNumber;
        SofaSize = sofaSize;
        SofasNumber = sofasNumber;
        FridgeSize = fridgeSize;
        FridgeNumber = fridgeNumber;
        TableSize = tableSize;
        TableNumber = tableNumber;
        Helper = helper;
        Date = date;
        AdditionalInfo = additionalInfo;
    }

    public String getBedSize() {
        return BedSize;
    }

    public void setBedSize(String bedSize) {
        BedSize = bedSize;
    }

    public int getBedNumber() {
        return BedNumber;
    }

    public void setBedNumber(int bedNumber) {
        BedNumber = bedNumber;
    }

    public String getSofaSize() {
        return SofaSize;
    }

    public void setSofaSize(String sofaSize) {
        SofaSize = sofaSize;
    }

    public int getSofasNumber() {
        return SofasNumber;
    }

    public void setSofasNumber(int sofasNumber) {
        SofasNumber = sofasNumber;
    }

    public String getFridgeSize() {
        return FridgeSize;
    }

    public void setFridgeSize(String fridgeSize) {
        FridgeSize = fridgeSize;
    }

    public int getFridgeNumber() {
        return FridgeNumber;
    }

    public void setFridgeNumber(int fridgeNumber) {
        FridgeNumber = fridgeNumber;
    }

    public String getTableSize() {
        return TableSize;
    }

    public void setTableSize(String tableSize) {
        TableSize = tableSize;
    }

    public int getTableNumber() {
        return TableNumber;
    }

    public void setTableNumber(int tableNumber) {
        TableNumber = tableNumber;
    }

    public String getHelper() {
        return Helper;
    }

    public void setHelper(String helper) {
        Helper = helper;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAdditionalInfo() {
        return AdditionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        AdditionalInfo = additionalInfo;
    }
}
