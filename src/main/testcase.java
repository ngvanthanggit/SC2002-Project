package main;

import java.util.Scanner;

import inventory.InventoryItem;
import inventory.InventoryManager;
import inventory.ReplenishManager;
import inventory.ReplenishRequest;

import java.util.List;

//test case for Inventory & Pharmacist Portion
public class testcase {
    private static String format = "|%-25s|\n";

    public static void main(String[] args) {
        
        InventoryManager.loadInventory(true);
        for(InventoryItem item : InventoryManager.getInventory()) {
            System.out.println("Item: " + item.getItemName() + ", Quantity: " + item.getQuantity());
        }

        ReplenishManager.loadReplenish(true);
        for(ReplenishRequest request : ReplenishManager.getReplenishList()) {
            System.out.println("ReqID: " + request.getRequestID() + "ItemName: " + request.getItemName());
            System.out.println(request.getRequestedBy());
            System.out.println(request.getRequestDate());
            System.out.println(request.getRequestStatus());
            System.out.println(request.getApprovalDate());
            
        }
        //System.out.println("Current working directory: " + System.getProperty("user.dir"));
        
        
    }
    
}

