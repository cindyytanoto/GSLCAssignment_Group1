package repository;

import utility.Connection;

import java.util.ArrayList;

import model.Model;
import model.Team;
import model.User;

public class UserRepository implements Repository {

	@Override
	public void find(String columnName, String[] conditions, boolean joinTable, String joinTableName, Connection conn) {
		ArrayList<User> userList = new ArrayList<>();

		if (joinTable && joinTableName != null) {
			joinTableFind(columnName, conditions, joinTableName, conn);
		}

		ArrayList<User> result = new ArrayList<>();

		for (User user : userList) {
			if (checkConditions(user, columnName, conditions)) {
				result.add(user);
			}
		}

		if (!result.isEmpty()) {
			System.out.println("Search results:");
			for (User user : result) {
				System.out.println(user);
			}
		} else {
			System.out.println("No matching data found.");
		}
	}

	@Override
	public void findOne(String columnName, String[] conditions, boolean joinTable, String joinTableName,
			Connection conn) {
		ArrayList<User> userList = conn.readUser();

		if (joinTable && joinTableName != null) {
			joinTableFindOne(columnName, conditions, joinTableName, conn);
		} else {
			User foundUser = null;

			for (User user : userList) {
				if (checkConditions(user, columnName, conditions)) {
					foundUser = user;
					break;
				}
			}

			if (foundUser != null) {
				System.out.println("Search result:");
				System.out.println(foundUser);
			} else {
				System.out.println("No matching data found.");
			}
		}
	}

	@Override
	public void insert(Model model, Connection conn) {
		User user = (User) model;
		conn.writeUser(user.getNim(), user.getUserName(), user.getIdTeam());
	}

	private void joinTableFind(String columnName, String[] conditions, String joinTableName, Connection conn) {
		ArrayList<User> userList = conn.readUser();
		ArrayList<User> result = new ArrayList<>();

		if (joinTableName.equalsIgnoreCase("team")) {
			ArrayList<Team> teamList = conn.readTeam();

			for (User user : userList) {
				for (Team team : teamList) {
					if (user.getIdTeam() == team.getIdTeam()) {
						if (checkConditions(user, columnName, conditions)) {
							result.add(user);
						}
						break;
					}
				}
			}
		}

		if (!result.isEmpty()) {
			System.out.println("Search results:");
			for (User user : result) {
				System.out.println(user);
			}
		} else {
			System.out.println("No matching data found.");
		}
	}

	private void joinTableFindOne(String columnName, String[] conditions, String joinTableName, Connection conn) {
		ArrayList<User> userList = conn.readUser();
		User foundUser = null;

		if (joinTableName.equalsIgnoreCase("team")) {
			ArrayList<Team> teamList = conn.readTeam();

			for (User user : userList) {
				for (Team team : teamList) {
					if (user.getIdTeam() == team.getIdTeam()) {
						if (checkConditions(user, columnName, conditions)) {
							foundUser = user;
							break;
						}
						break;
					}
				}

				if (foundUser != null) {
					break;
				}
			}
		}

		if (foundUser != null) {
			System.out.println("Search result:");
			System.out.println(foundUser);
		} else {
			System.out.println("No matching data found.");
		}
	}

	private boolean checkConditions(User user, String columnName, String[] conditions) {
		if (columnName == null || conditions == null) {
			return true;
		}

		for (int i = 0; i < conditions.length; i += 2) {
			String conditionColumn = conditions[i];
			String operator = conditions[i + 1];
			String conditionValue = conditions[i + 2];

			switch (operator) {
			case "=":
				if (columnName.equals(conditionColumn)
						&& user.matchesCondition(conditionColumn, operator, conditionValue)) {
					return true;
				}
				break;

			}
		}

		return false;
	}

	@Override
	public void show(String columnName, String operator, String keyword, Connection conn) {
		ArrayList<User> userList = conn.readUser();

		System.out.println("Users:");
		if (columnName == null || operator == null || keyword == null) {
			for (User user : userList) {
				System.out.println(user);
			}
		} else {
			for (User user : userList) {
				if (user.matchesCondition(columnName, operator, keyword)) {
					System.out.println(user);
				}
			}
		}
	}

}
