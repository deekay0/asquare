package edu.cmu.square.client.ui.risksAssessment;

public class RiskExposure {
	
	public static String   getExposure(int impact,int likelihood)
	{
		int result=0;
		if(impact==1 && likelihood==1)
		{
			result=1;
		}
		else if(impact==1 && likelihood==2)
		{
			result=2;
		}
		else if(impact==1 && likelihood==3)
		{
			result=3;
		}
		
		else if(impact==2 && likelihood==1)
		{
			result=2;
		}
		else if(impact==2 && likelihood==2)
		{
			result=2;
		}
		else if(impact==2 && likelihood==3)
		{
			result=3;
		}
		else if(impact==3 && likelihood==1)
		{
			result=3;
		}
		else if(impact==3 && likelihood==2)
		{
			result=3;
		}
		else if(impact==3 && likelihood==3)
		{
			result=3;
		}
	
		String exposure="Unknown";
		if(result==1)
		{
			exposure="High";
		}
		else if(result==2)
		{
			exposure="Medium";
		}
		else if(result==3)
		{
			exposure="Low";
		}
		return  exposure;
	}
}
