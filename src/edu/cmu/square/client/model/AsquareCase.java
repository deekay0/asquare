package edu.cmu.square.client.model;

public enum AsquareCase
{
		Case1("Case 1"), 
		Case2("Case 2"), 
		Case3("Case 3");
		//None("None");
			
		private String label;
		
		private AsquareCase(String label) {
			this.label = label;
		}
		public String getLabel() {
			return this.label;
		}
		
		

}
