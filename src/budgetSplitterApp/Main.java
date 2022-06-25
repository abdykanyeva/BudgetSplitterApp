package budgetSplitterApp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to the Budget Splitter App");

        System.out.println("How many people in the Budget Planner?");

        ArrayList<User> userList = prepareUserLists(scan);

        ArrayList<Expense> expenseList = new ArrayList<>();

        System.out.println("Added user count: " + userList.size());

        while (true) {

            System.out.println("What would you like to do? ");

            for(int i = 0; i < prepareOptionList().length; i++){
                System.out.println(prepareOptionList()[i] + ": " + (i + 1));
            }

            int request = scan.nextInt();



            switch(request - 1 ){
                case 0:
                    Expense expense = new Expense();

                    System.out.println("Expense name");
                    expense.expenseName = scan.next();

                    System.out.println("Expense amount");
                    expense.amount = scan.nextInt();

                    System.out.println("Which user made this expense? Just type user Id");

                    for(User each : userList){
                        System.out.println("id: " +userList.indexOf(each) + " name: " + each.name);
                    }

                    int userId = scan.nextInt();
                    User user = userList.get(userId);
                    expense.user = user.name;

                    expenseList.add(expense);

                    break;

                case 1:

                    System.out.println("Please provide user name that you would like to search");
                    String username = scan.next();

                    User myUser = null;

                    for(User chosenUser:userList){
                        if(chosenUser.name.equals(username)){
                            myUser = chosenUser;
                            break;
                        }
                    }
                    if(myUser == null){
                        System.out.println("User not exists!");
                        break;
                    }

                    int userExpenseAmount = 0;
                    int expenseCount = 0;
                    for (int i = 0; i < expenseList.size(); i++) {

                        if(expenseList.get(i).user.equals(username)){
                            userExpenseAmount+= expenseList.get(i).amount;
                            expenseCount++;

                            System.out.println(expenseCount + " -expense amount:" + expenseList.get(i).amount + " ,expense by: " + expenseList.get(i).user);
                        }
                    }
                    System.out.println(myUser.name + " spent $ " + userExpenseAmount);

                    break;
                case 2:
                    for (int i = 0; i < expenseList.size(); i++) {
                        System.out.println(i + "  expense amount " +  expenseList.get(i) .amount + ", expense by:  " + expenseList.get(i).user);
                    }

                    break;
                case 3:
                    double totalAmount = 0;

                    ArrayList<Split> splitList = calculateSplitByUser(expenseList);

                    for(Split split:splitList){
                        totalAmount += split.amount;
                    }
                    makeSplit(totalAmount, splitList);

                    break;
                case 4:

                    for(User allUser : userList){
                        System.out.println(allUser.name + ": " + allUser.email);
                    }

                    break;
                case 5:
                    System.exit(0);

            }
        }






    }

    public static void makeSplit(double totalAmount, ArrayList<Split> splitList) {

        double amount = totalAmount / splitList.size();

        for(Split split:splitList){

            if(split.amount > amount){
                System.out.println(split.userName + " needs to take back " + (split.amount - amount));
            }else{
                System.out.println(split.userName + " needs to give " + (amount - split.amount));
            }
        }

    }

    public static ArrayList<Split> calculateSplitByUser(ArrayList<Expense> expenseList) {

        ArrayList<Split> splitList = new ArrayList<>();

        for(Expense expense : expenseList){
            
            Split split = existSplitList(expense.user, splitList);

            if(split != null){
                split.amount += expense.amount;
            }else{
                Split willBeAdded = new Split();
                willBeAdded.userName = expense.user;
                willBeAdded.amount = expense.amount;
                splitList.add(willBeAdded);

            }

        }





        return splitList;

    }

    public static Split existSplitList(String user, ArrayList<Split> splitList) {
        for(Split split :splitList){
            if(split.userName.equals(user)) {
                return split;
            }
        }
        return null;
    }

    public static ArrayList<User> prepareUserLists(Scanner scan){
        int userCount = scan.nextInt();
        ArrayList<User> userList = new ArrayList<>();

        for(int i = 0; i < userCount; i++){

            User user = new User();

            System.out.println("Name:");
            user.name = scan.next();

            System.out.println("Email");
            user.email = scan.next();

            userList.add(user);

        }
        return userList;

    }

    public static String[] prepareOptionList(){

        String [] optionList = {"Make Expense", "List Specific Person Expense", "List All Expense",
                "Make Split", "List All Users", "Close the budget "};
        return optionList;

         /*
    0 : Make expense
    1: List Specific Person Expense
    2: List All Expenses
    3: MAKE sPLIT
    4: List All Users
    5: Close the budget

     */
    }



}

