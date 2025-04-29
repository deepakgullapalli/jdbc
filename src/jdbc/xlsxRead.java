package jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class xlsxRead {
	public static void main(String[] args) throws IOException {

		try {

			jdbc_con jdbc = new jdbc_con();
			String filePath = "C:\\Users\\NICHEBIT\\Downloads\\testingxlsxv.xlsx";
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);

//			XSSFWorkbook wb = new XSSFWorkbook(fis);
			Workbook workbook = null;

			if (filePath.endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(fis);
				System.out.println("XSSF");
			} else if (filePath.endsWith(".xls")) {
				System.out.println("HSSF");
				workbook = new HSSFWorkbook(fis);
			}
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> itr = sheet.iterator();
			String[] StA = { "Cordys No", "Cancelled / Endorsement Proposal No", "UTR Number", "UTR Date", "UTR amount",
					"Customer Account No", "IFSC code" };
			String[] DataTypes = { "Numeric", "Numeric", "Varchar", "DD/MM/YYYY", "Numeric", "Varchar", "Varchar" };
			int rowCount = sheet.getLastRowNum() + 1;
			System.out.println("fgjhj" + rowCount);
			int rowno = 0;

			while (itr.hasNext()) {

				Row row = itr.next();
				;
				rowno++;

				if (rowno == 1) {
					if (row.getLastCellNum() == 7) {
						System.out.println("fefef" + row.getLastCellNum());
					} else {

						break;
					}
				} else {
					System.out.println("not breaking");
					if (rowno <= rowCount) {

						for (int j = 0; j < row.getLastCellNum(); j++) {
							double numericValue = row.getCell(3).getNumericCellValue();
							java.util.Date dateValue = DateUtil.getJavaDate(numericValue);
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
							String UTRDate = dateFormat.format(dateValue);
							
							//String UTRDate = "29/12/2023";
							
							
							if (j == 0) {
								if (row.getCell(j).getNumericCellValue() != 0.0) {
									
									

									double cordysNo = row.getCell(j).getNumericCellValue();
//										  System.out.println(cordysNo);
//										jdbc_con jdbc = new jdbc_con();
									ResultSet datas = jdbc.Conn_db_to_get(
											"select * from ENDORSEMNT_REFUND_DETAILS WHERE ENDORSEMENT_ID=?", cordysNo);

									if (!datas.next()) {
										System.out.println("No data");

										String returnStr = jdbc.Conn_db_to_insert("",3,0,row.getCell(0).getNumericCellValue(),0,row.getCell(2).getStringCellValue(),  row.getCell(4).getNumericCellValue(),row.getCell(5).getStringCellValue(),row.getCell(6).getStringCellValue(), "Not Valid",
												"With This CordysId There Is No Data Available",UTRDate);
										System.out.println(returnStr);

										break;

									} else {

										System.out.println("data avai");

//								             do {
//								            	 
//									                System.out.println(datas.getDouble(3));
//									            } while (datas.next());
//								             System.out.println("Data Available");
//									
									}
								}
							}

							
							if (j == 3) {
								if (row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC
										&& DateUtil.isCellDateFormatted(row.getCell(j))) {

									if (row.getCell(j).getNumericCellValue() != 0.0) {
//										double numericValue = row.getCell(j).getNumericCellValue();
//										java.util.Date dateValue = DateUtil.getJavaDate(numericValue);
//										SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//										UTRDate = dateFormat.format(dateValue);
//										System.out.println(UTRDate);

										Date date = new Date();

										String formattedDate = dateFormat.format(date);
										System.out.println("formattedDate" + formattedDate);
										Date date1 = dateFormat.parse(UTRDate);
										System.out.println("date1" + date1);
										Date date2 = dateFormat.parse(formattedDate);

										int result = date1.compareTo(date2);
										System.out.println(result);

										if (result > 0) {

											String returnStr = jdbc.Conn_db_to_insert("",3,0,row.getCell(0).getNumericCellValue(),0,row.getCell(2).getStringCellValue(),  row.getCell(4).getNumericCellValue(),row.getCell(5).getStringCellValue(),row.getCell(6).getStringCellValue(), "Not Valid",
													"Hear  UTRDate Is Earliar Than Current Date",UTRDate);
											System.out.println(returnStr);

											break;
										}

									}

								}

							}

							if (j == 6) {
								System.out.println("uytuytu");
								String returnStr = jdbc.Conn_db_to_insert("",3,0,row.getCell(0).getNumericCellValue(),0,row.getCell(2).getStringCellValue(),  row.getCell(4).getNumericCellValue(),row.getCell(5).getStringCellValue(),row.getCell(6).getStringCellValue(), "Valid", "No Isuue",UTRDate);
								System.out.println(returnStr);

								String returnStr1 = jdbc.Conn_db_to_update("",row.getCell(2).getStringCellValue(), row.getCell(5).getStringCellValue(), row.getCell(6).getStringCellValue(),
										row.getCell(0).getNumericCellValue(),UTRDate,row.getCell(4).getNumericCellValue());

								System.out.println(returnStr1 + row.getCell(0).getNumericCellValue());
							}
							
						}

					}
				}

				
				
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
