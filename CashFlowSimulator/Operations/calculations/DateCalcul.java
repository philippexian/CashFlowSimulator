package calculations;

import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;

import assets.Asset;

public class DateCalcul {

	//calculate the time difference between 2 dates
	public static int difDate(Calendar refDate, Calendar targetDate, char periodicity){
		int number=0;//in fine
		long difInDays=(targetDate.getTimeInMillis()-refDate.getTimeInMillis())/(1000*60*60*24);
		int difInMonths=(targetDate.get(Calendar.YEAR)-refDate.get(Calendar.YEAR))*12+
				(targetDate.get(Calendar.MONTH)-refDate.get(Calendar.MONTH))+
				one(targetDate.get(Calendar.DAY_OF_MONTH), refDate.get(Calendar.DAY_OF_MONTH));

		if(periodicity=='D'){
			number=(int)difInDays;
		}
		if(periodicity=='W'){
			number=(int)(difInDays-1)/7+1;
		}
		if(periodicity=='M'){
			number=difInMonths;
		}
		if(periodicity=='Q'){
			number=(difInMonths-1)/3+1;
		}
		if(periodicity=='B'){
			number=(difInMonths-1)/6+1;
		}
		if(periodicity=='A'){
			number=(difInMonths-1)/12+1;
		}
		return number;
	}
	
	//EDate: calculate the corresponding date with a certain period number from the reference date
	public static Calendar EDate(Calendar refDate, int periodNumber, char periodicity){
		Calendar corresDate=(Calendar) refDate.clone();
		if(periodicity=='D'){
			corresDate.add(Calendar.DAY_OF_MONTH, periodNumber);
		}
		if(periodicity=='W'){
			corresDate.add(Calendar.DAY_OF_MONTH, 7*periodNumber);
		}
		if(periodicity=='M'){
			corresDate.add(Calendar.MONTH, periodNumber);
		}
		if(periodicity=='Q'){
			corresDate.add(Calendar.MONTH, 3*periodNumber);
		}
		if(periodicity=='B'){
			corresDate.add(Calendar.MONTH, 6*periodNumber);
		}
		if(periodicity=='A'){
			corresDate.add(Calendar.YEAR, periodNumber);
		}
		//in fine treated in financial asset
		return corresDate;
	}
	
	//parse from calendar to a integer list consisting of the year, month and day
	public static int[] parseCalendar(Calendar c){
		int[] yearMonthDay={
				c.get(Calendar.YEAR),
				c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH)
		};
		return yearMonthDay;
	}
	
	public static int[] setIntDate(int year, int month, int day){
		int[] intDate={year, month, day};
		return intDate;
	}
	
	public static Calendar getCalendarDate(int[] date){
		return new GregorianCalendar(date[0], date[1], date[2]);
	}
	
	//compare 2 dates, one in integer list the other in calendar
	public static int compareTwoDates(Calendar date, int[] refDate){//the ref date is the date stored in date list, in the form of int[]
		if(date.get(Calendar.YEAR)>refDate[0]){
			return 1;//after the ref date
		}
		else if(date.get(Calendar.YEAR)<refDate[0]){
			return -1;//before the ref date
		}
		else{
			if(date.get(Calendar.MONTH)>refDate[1]){
				return 1;
			}else if(date.get(Calendar.MONTH)<refDate[1]){
				return -1;
			}
			else{
				if(date.get(Calendar.DAY_OF_MONTH)>refDate[2]){
					return 1;
				}else if(date.get(Calendar.DAY_OF_MONTH)<refDate[2]){
					return -1;
				}
				else{
					return 0;//equal to the ref date
				}
			}
		}
	}
	
	public static int nbMonthsToNbPeriods(int nbMonths, Asset asset){
		return (int)CashflowAsset.getNbPeriodsPY(asset)*nbMonths/12;
	}
	
	//function affiliated to difDate
		public static int one(double num1, double num2){
			if(num1>num2){
				return 1;
			}
			else{
				return 0;
			}
		}
		
	public static Comparator<int[]> myComparator=new Comparator<int[]>(){

		@Override
		public int compare(int[] o1, int[] o2) {
			if(o1[0]>o2[0]){
				return 1;
			}
			else if(o1[0]<o2[0]){
				return -1;
			}
			else{
				if(o1[1]>o2[1]){
					return 1;
				}
				else if(o1[1]<o2[1]){
					return -1;
				}
				else{
					if(o1[2]>o2[2]){
						return 1;
					}
					else if(o1[2]<o2[2]){
						return -1;
					}
					else{
						return 0;
					}
				}
			}
		}
		
	};
	

}
