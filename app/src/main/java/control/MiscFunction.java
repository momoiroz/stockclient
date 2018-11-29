package control;

import com.google.gson.JsonElement;

import java.util.*;

import model.MatchModel;
import model.OrderModel;

public class MiscFunction {
    public ArrayList<OrderModel> convertJsonToOrderObject(JsonElement element) {
        String json = element.toString();
        json = json.replace("\"","");
        Stack<Character> stack = new Stack();
        stack.push(json.charAt(0));
        int iter = 1;
        int iterSArray = 0;
        String[] store = new String[19];
        StringBuilder tmp = new StringBuilder();
        ArrayList<OrderModel> model = new ArrayList();
        while (!stack.isEmpty()) {
            if (iter == json.length()-1) {
                store[iterSArray] = tmp.toString();
                OrderModel m = new OrderModel(store);
                model.add(m);
                iterSArray = 0;
            }
            if (json.charAt(iter) == '{') {
                stack.push(json.charAt(iter));
            } else if (json.charAt(iter) == '}') {
                stack.pop();
            } else if (json.charAt(iter) == ':' || json.charAt(iter) == ',') {
                store[iterSArray] = tmp.toString();
                tmp = new StringBuilder();
                if (iterSArray == 18) {
                    OrderModel m = new OrderModel(store);
                    model.add(m);
                    iterSArray = 0;
                } else {
                    iterSArray++;
                }
            } else {
                tmp.append(json.charAt(iter));
            }
            iter++;
        }
        return model;
    }
    public ArrayList<MatchModel> convertJsonToMatchObject(JsonElement element) {
        String json = element.toString();
        json = json.replace("\"","");
        Stack<Character> stack = new Stack();
        stack.push(json.charAt(0));
        int iter = 1;
        int iterSArray = 0;
        String[] store = new String[25];
        StringBuilder tmp = new StringBuilder();
        ArrayList<MatchModel> model = new ArrayList();
        while (!stack.isEmpty()) {
            if (iter == json.length()-1) {
                store[iterSArray] = tmp.toString();
                MatchModel m = new MatchModel(store);
                model.add(m);
                iterSArray = 0;
            }
            if (json.charAt(iter) == '{') {
                stack.push(json.charAt(iter));
            } else if (json.charAt(iter) == '}') {
                stack.pop();
            } else if (json.charAt(iter) == ':' || json.charAt(iter) == ',') {
                store[iterSArray] = tmp.toString();
                tmp = new StringBuilder();
                if (iterSArray == 24) {
                    MatchModel m = new MatchModel(store);
                    model.add(m);
                    iterSArray = 0;
                } else {
                    iterSArray++;
                }
            } else {
                tmp.append(json.charAt(iter));
            }
            iter++;
        }
        return model;
    }
}
