package DBManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import Assignments.Discussion;
import Assignments.Program;
import Assignments.Quiz;
import GradebookMenu.Assignment;
import GradebookMenu.Gradebook;


public class GradebookDB {
	
	public static void insertAll() {
		String tableSQL = "Create TABLE IF NOT EXISTS Gradebook (\n" +
						"GradebookID INT PRIMARY KEY AUTO_INCREMENT,\n" +
						"Type VARCHAR(20) NOT NULL,\n" +
						"Name VARHCAR(100) NOT NULL,\n" +
						"Score DECIMAL NOT NULL,\n" +
						"Letter char NOT NULL,\n" +
						"Date VARCHAR(20) NOT NULL,\n" +
						"Special VARCHAR(100) NOT NULL\n" +
						");";
		
		String sql = "INSERT INTO Gradebook " + 
					"(Type, Name, Score, Letter, Date, Special) " +
					"VALUES " +
					"(?, ?, ?, ?, ?, ?)";
		Statement s;
		PreparedStatement ps;
		try {
			//create the table if it doesnt exist
			s = DBUtil.getConnection().createStatement();
			s.execute(tableSQL);
			
			//insert the grades
			ps = DBUtil.getConnection().prepareStatement(sql);
			for(Assignment a : Gradebook.gradebook) {
				//if its a quiz
				if(a instanceof Quiz) {
					ps.setString(1, "Quiz");
					ps.setString(2, a.getName());
					ps.setDouble(3, a.getScore());
					ps.setString(4, String.valueOf(a.getLetter()));
					ps.setString(5, "" + a.getDate());
					ps.setString(6, "" + ((Quiz)a).getQuestionCount());
					
					//if its a discussion
				} else if(a instanceof Discussion) {
					ps.setString(1, "Discussion");
					ps.setString(2, a.getName());
					ps.setDouble(3, a.getScore());
					ps.setString(4, String.valueOf(a.getLetter()));
					ps.setString(5, "" + a.getDate());
					ps.setString(6, ((Discussion)a).getReading());
					
					//if its a program
				} else if(a instanceof Program) {
					ps.setString(1, "Program");
					ps.setString(2, a.getName());
					ps.setDouble(3, a.getScore());
					ps.setString(4, String.valueOf(a.getLetter()));
					ps.setString(5, "" + a.getDate());
					ps.setString(6, ((Program)a).getConcept());
				}
				//execute the sql code
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void getAll() {
		
		try (Statement statement = DBUtil.getConnection().createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM Gradebook")) {
            while (rs.next()) {
            	
            	String type = rs.getString("Type");
            	
            	if(type.equals("Quiz")) {
            		String name = rs.getString("Name");
					Double score = rs.getDouble("Score");
					char letter = rs.getString("Letter").charAt(0);
					LocalDate date = LocalDate.parse(rs.getString("Date"));
					int questions = Integer.parseInt(rs.getString("Special"));
					Quiz quiz = new Quiz(score, letter, name, date, questions);
					
					//check if its a duplicate
					boolean isDuplicate = false;
					for(Assignment a : Gradebook.gradebook) {
						if(a instanceof Quiz) {
							if(quiz.getName().equals(a.getName()) && quiz.getScore() == a.getScore() &&
									quiz.getDate().compareTo(a.getDate()) == 0 && 
									quiz.getQuestionCount() == ((Quiz)a).getQuestionCount()) {
								isDuplicate = true;
								break;
							}
						}
					}
					if(isDuplicate == false) {
						Gradebook.gradebook.add(quiz);
					}
					//if its a discussion
				} else if(type.equals("Discussion")) {
					String name = rs.getString("Name");
					Double score = rs.getDouble("Score");
					char letter = rs.getString("Letter").charAt(0);
					LocalDate date = LocalDate.parse(rs.getString("Date"));
					String reading = rs.getString("Special");
					Discussion discussion = new Discussion(score, letter, name, date, reading);
					
					//check if its a duplicate
					boolean isDuplicate = false;
					for(Assignment a : Gradebook.gradebook) {
						if(a instanceof Quiz) {
							if(discussion.getName().equals(a.getName()) && discussion.getScore() == a.getScore() &&
									discussion.getDate().compareTo(a.getDate()) == 0 && 
									discussion.getReading().equals(((Discussion)a).getReading())) {
								isDuplicate = true;
								break;
							}
						}
					}
					if(isDuplicate == false) {
						Gradebook.gradebook.add(discussion);
					}
					//if its a program
				} else if(type.equals("Program")) {
					String name = rs.getString("Name");
					Double score = rs.getDouble("Score");
					char letter = rs.getString("Letter").charAt(0);
					LocalDate date = LocalDate.parse(rs.getString("Date"));
					String concept = rs.getString("Special");
					Program program = new Program(score, letter, name, date, concept);
					
					//check if its a duplicate
					boolean isDuplicate = false;
					for(Assignment a : Gradebook.gradebook) {
						if(a instanceof Quiz) {
							if(program.getName().equals(a.getName()) && program.getScore() == a.getScore() &&
									program.getDate().compareTo(a.getDate()) == 0 && 
									program.getConcept().equals(((Program)a).getConcept())) {
								isDuplicate = true;
								break;
							}
						}
					}
					if(isDuplicate == false) {
						Gradebook.gradebook.add(program);
					}
				}
            }
            System.out.println();
        } catch (SQLException e) {
            System.out.println(e);
        }
		
	}
	
	
	
}
