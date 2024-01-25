import java.util.*;

class Friend {
    String name;
    boolean closeFriend;

    private static final List<Friend> allFriends = new ArrayList<>();

    // Create Friend
    public Friend(String name, boolean closeFriend) {
        this.name = name;
        this.closeFriend = closeFriend;

        allFriends.add(this);
    }

    // View All Friends
    public static List<Friend> getAllFriends() {
        return allFriends;
    }

    // Remove Friend
    public static void removeFriend(String friendName) {
        Iterator<Friend> iterator = allFriends.iterator();
        while (iterator.hasNext()) {
            Friend friend = iterator.next();
            if (friend.name.equals(friendName)) {
                iterator.remove();
                break;
            }
        }
    }

    public void changeCloseStatus() {
        closeFriend = !closeFriend;
    }


    public static Friend findOneFriend(String friendName) {
        for (Friend friend : allFriends) {
            if (friend.name.equals(friendName)) {
                return friend;
            }
        }
        return null;
    }

}

public class Main {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("""
                        ###### Friends List Creator ######
                        |--------------------------------|
                        | [1] Add Friends                |
                        | [2] Remove Friends             |
                        | [3] Update Friends             |
                        | [4] View Friends               |
                        | [5] View Favorites             |
                        | [6] Quit Program               |
                        |--------------------------------|
                        ######     Java Edition     ######
                        """);
                byte input = scanner.nextByte();
                scanner.nextLine();
                switch (input) {
                    case 1: // Add Friend
                        System.out.println("Who are you adding to your friends list?");
                        String friendName = scanner.nextLine();
                        System.out.println("Are they your close friend? [y/n]");
                        String response = scanner.nextLine();
                        boolean closeFriend;
                        closeFriend = response.equalsIgnoreCase("y");
                        new Friend(friendName, closeFriend);
                        continue;

                    case 2: // Remove Friend
                        for (byte i = 0; i < Friend.getAllFriends().size(); i++) {
                            System.out.println((i + 1) + ": " + Friend.getAllFriends().get(i).name);
                        }
                        System.out.println("Who will you remove from your friends list?");
                        String target = scanner.nextLine();
                        System.out.println("Are you sure you want to delete " + target + "? [y/n]");
                        String confirm = scanner.nextLine();
                        if (confirm.equalsIgnoreCase("y")) {
                            try {
                                Friend.removeFriend(target);
                                System.out.println("Friend " + target + " was removed from your list.");
                            } catch (NoSuchElementException e) {
                                System.out.println("No friends by the name" + target + ".");
                            }
                        } else {
                            System.out.println("No one was removed. ");
                        }
                        continue;

                    case 3: // Update Friend
                        System.out.println("Which friend do you want to update?");
                        String person = scanner.nextLine();
                        System.out.println("What will you change? [n]ame or [f]avorite");
                        String option = scanner.nextLine();
                        try {
                            Friend selected = Friend.findOneFriend(person);
                            if (option.equalsIgnoreCase("f")) {
                                assert selected != null;
                                selected.changeCloseStatus();

                            } else if (option.equalsIgnoreCase("n")) {
                                assert selected != null;
                                System.out.println("What should their new name be?");
                                selected.name = scanner.nextLine();

                            } // Target not found
                        } catch (NullPointerException e) {
                            System.out.println("Friend " + person + " was not found.");
                            scanner.nextLine();

                        }
                        continue;

                    case 4: // View Friends
                        int friendCount = Friend.getAllFriends().size();
                        if (friendCount != 0) {
                            System.out.println("You are friends with the following " + friendCount + " friends.");
                            for (byte i = 0; i < Friend.getAllFriends().size(); i++) {
                                System.out.println((i + 1) + ": " + Friend.getAllFriends().get(i).name);
                            }
                        } else {
                            System.out.println("Your friends list is empty. ");
                        }
                        continue;
                    case 5: // View Favorite Friends
                        List<String> favoriteList = getStringList();
                        String[] favorites = favoriteList.toArray(new String[0]);
                        if (!favoriteList.isEmpty()) {
                            System.out.println("You have " + favoriteList.size() + " favorite friends.");
                            for (String favorite : favorites) {
                                System.out.println(favorite);
                            }
                        } else {
                            System.out.println("Your favorites list is empty.");
                        }
                        continue;

                    case 6: // Quit the program, Exit try block
                        return;
                    default:
                        // nothing (it will continue the loop)
                }

            }

        }

    }
    // For use with favorites list
    private static List<String> getStringList() {
        List<Friend> allFriendsList = Friend.getAllFriends();
        List<String> favoriteList = new ArrayList<>();

        for (byte i = 0; i < allFriendsList.size(); i++) {
            Friend friend = allFriendsList.get(i);
            if (friend.closeFriend) {
                String favoriteFriend = i + ": " + friend.name;
                favoriteList.add(favoriteFriend);
            }
        }
        return favoriteList;
    }
}