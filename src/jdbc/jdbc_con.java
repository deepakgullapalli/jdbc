package jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class jdbc_con {

    public  ResultSet Conn_db_to_get(String QueryData,double dq) {
        ResultSet rs1 = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection c1 = DriverManager.getConnection("jdbc:oracle:thin:@192.168.2.18:1521:orcl", "training",
                    "training");

            PreparedStatement ps = c1.prepareStatement(QueryData);    
            ps.setDouble(1, dq);
            rs1 = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs1;
    }

    
    
    public  String  Conn_db_to_insert(String QueryData,double ID,double TRANSACTION_ID,double ENDORSEMENT_ID,double ENDORSEMENT_PROPOSAL_NO,String UTR_NUMBER,double UTR_AMOUNT,String CUSTOMER_ACCOUNT_NO,String IFSC_CODE,String STATUS,String REASON,String UTRDate) {
        String Status ="";
        System.out.println(UTR_NUMBER);
        try {
        	 String sql = "INSERT INTO TRAINING.ENDORSEMNT_ACC_UPLOAD_STS (ID, TRANSACTION_ID, ENDORSEMENT_ID, ENDORSEMENT_PROPOSAL_NO, UTR_NUMBER, UTR_DATE, UTR_AMOUNT, CUSTOMER_ACCOUNT_NO, IFSC_CODE, STATUS, REASON) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        	 Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.2.18:1521:orcl", "training",
                     "training");
        	 
        	 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        	 java.util.Date utilDate = dateFormat.parse(UTRDate);
        	 java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        	 PreparedStatement	 preparedStatement = connection.prepareStatement(sql);
        	 
        	  preparedStatement.setDouble(1,ID );
              preparedStatement.setDouble(2,TRANSACTION_ID);
              preparedStatement.setDouble(3,ENDORSEMENT_ID );
              preparedStatement.setDouble(4,ENDORSEMENT_PROPOSAL_NO );
              preparedStatement.setString(5,UTR_NUMBER );
              preparedStatement.setDate(6,sqlDate);
              preparedStatement.setDouble(7, UTR_AMOUNT);
              preparedStatement.setString(8,CUSTOMER_ACCOUNT_NO );
              preparedStatement.setString(9,IFSC_CODE);
              preparedStatement.setString(10,STATUS);
              preparedStatement.setString(11,REASON);
              int rowsAffected = preparedStatement.executeUpdate();

              if (rowsAffected > 0) {
            	  Status="Insert successful.";
              } else {
                 Status ="Insert failed.";
              }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Status;
    }
    
    

    public  String  Conn_db_to_update(String QueryData,String UTRNumber,String CustomerAccountNo,String IFSCCode,double endoresmentid,String UTRDate,double UTRAmount) {
        String Status ="";
     
        try {
        	
        	 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        	 java.util.Date utilDate = dateFormat.parse(UTRDate);
        	 java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        	 String sql = "UPDATE ENDORSEMNT_REFUND_DETAILS SET  UTR_NUMBER =?,CUSTOMER_ACCOUNT_NO=?,IFSC_CODE=?,UTR_DATE=?,UTR_AMOUNT=?  WHERE ENDORSEMENT_ID =? ";
        	 Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.2.18:1521:orcl", "training",
                     "training");
        	 PreparedStatement	 preparedStatement = connection.prepareStatement(sql);

        	    preparedStatement.setString(1,UTRNumber);
        	    preparedStatement.setString(2,CustomerAccountNo);
        	    preparedStatement.setString(3,IFSCCode);
        	    preparedStatement.setDate(4,sqlDate);
        	    preparedStatement.setDouble(5,UTRAmount);
        	    preparedStatement.setDouble(6,endoresmentid);
              int rowsAffected = preparedStatement.executeUpdate();

              if (rowsAffected > 0) {
            	  Status="Insert successful.";
              } else {
                 Status ="Insert failed.";
              }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Status;
    }
    
    
    
    
    
    
    
    
    
    

//    public static void main(String args[]) throws ClassNotFoundException, SQLException {
//
//    	double d=36985;
//        ResultSet datas = Conn_db_to_get("select * from ENDORSEMNT_REFUND_DETAILS WHERE ENDORSEMENT_ID=?",d);
//        if (!datas.next()) {
//            System.out.println("No data");
//        } else {
//            do {
//                System.out.println(datas.getString(2));
//            } while (datas.next());
//	
//       
//       
//    }
//    }
}

