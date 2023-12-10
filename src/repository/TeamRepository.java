package repository;

import utility.Connection;
import java.util.ArrayList;

import model.Model;
import model.Team;
import model.User;

public class TeamRepository implements Repository {

    @Override
    public void find(String columnName, String[] conditions, boolean joinTable, String joinTableName, Connection conn) {
        ArrayList<Team> teamList = conn.readTeam();

        if (joinTable && joinTableName != null) {
            joinTableFind(columnName, conditions, joinTableName, conn);
        } else {
            ArrayList<Team> result = new ArrayList<>();

            for (Team team : teamList) {
                if (checkConditions(team, columnName, conditions)) {
                    result.add(team);
                }
            }

            if (!result.isEmpty()) {
                System.out.println("Search results:");
                for (Team team : result) {
                    System.out.println(team);
                }
            } else {
                System.out.println("No matching data found.");
            }
        }
    }

    @Override
    public void findOne(String columnName, String[] conditions, boolean joinTable, String joinTableName, Connection conn) {
        ArrayList<Team> teamList = conn.readTeam();

        if (joinTable && joinTableName != null) {
            joinTableFindOne(columnName, conditions, joinTableName, conn);
        } else {
            Team foundTeam = null;

            for (Team team : teamList) {
                if (checkConditions(team, columnName, conditions)) {
                    foundTeam = team;
                    break;
                }
            }

            if (foundTeam != null) {
                System.out.println("Search result:");
                System.out.println(foundTeam);
            } else {
                System.out.println("No matching data found.");
            }
        }
    }

    @Override
    public void insert(Model model, Connection conn) {
        Team team = (Team) model;
        conn.writeTeam(team.getIdTeam(), team.getTeamName());
    }

    private boolean checkConditions(Team team, String columnName, String[] conditions) {
        if (columnName == null || conditions == null) {
            return true; 
        }

        for (int i = 0; i < conditions.length; i += 2) {
            String conditionColumn = conditions[i];
            String operator = conditions[i + 1];
            String conditionValue = conditions[i + 2];

            switch (operator) {
                case "=":
                    if (columnName.equals(conditionColumn) && team.matchesCondition(conditionColumn, operator, conditionValue)) {
                        return true;
                    }
                    break;
                
            }
        }

        return false;
    }
    
    private void joinTableFind(String columnName, String[] conditions, String joinTableName, Connection conn) {
        ArrayList<Team> teamList = conn.readTeam();
        ArrayList<User> userList = conn.readUser();

        ArrayList<Team> result = new ArrayList<>();

        for (Team team : teamList) {
            for (User user : userList) {
                if (team.getIdTeam() == user.getIdTeam()) {
                    if (checkConditions(team, columnName, conditions)) {
                        result.add(team);
                    }
                    break; 
                }
            }
        }

        if (!result.isEmpty()) {
            System.out.println("Search results:");
            for (Team team : result) {
                System.out.println(team);
            }
        } else {
            System.out.println("No matching data found.");
        }
    }


    private void joinTableFindOne(String columnName, String[] conditions, String joinTableName, Connection conn) {
        ArrayList<Team> teamList = conn.readTeam();
        Team foundTeam = null;

        if (joinTableName.equalsIgnoreCase("user")) {
            ArrayList<User> userList = conn.readUser();

            for (Team team : teamList) {
                for (User user : userList) {
                    if (team.getIdTeam() == user.getIdTeam()) {
                        if (checkConditions(team, columnName, conditions)) {
                            foundTeam = team;
                            break;
                        }
                        break;
                    }
                }

                if (foundTeam != null) {
                    break; 
                }
            }
        }

        if (foundTeam != null) {
            System.out.println("Search result:");
            System.out.println(foundTeam);
        } else {
            System.out.println("No matching data found.");
        }
    }

	@Override
	public void show(String columnName, String operator, String keyword, Connection conn) {
		ArrayList<Team> teamList = conn.readTeam();

        System.out.println("Teams:");
        if (columnName == null || operator == null || keyword == null) {
            for (Team team : teamList) {
                System.out.println(team);
            }
        } else {
            for (Team team : teamList) {
                if (team.matchesCondition(columnName, operator, keyword)) {
                    System.out.println(team);
                }
            }
        }
	}
}
