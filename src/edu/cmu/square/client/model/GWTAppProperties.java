package edu.cmu.square.client.model;
import java.io.Serializable;


public class GWTAppProperties implements Serializable
{

	private static final long serialVersionUID = -5742393700416141068L;
	
	private String headerTitle1;
	private String headerTitle2;
	private String headerLogo;
	
	private String footerTitle;
	private String footerLinkOrganizationName;
	private String footerOrganizationURL;
	private String footerLabelCopyRight;
	private String helpLink;


	public void setHeaderTitle1(String headerTitle1) {
		this.headerTitle1 = headerTitle1;
	}

	public String getHeaderTitle1() {
		return headerTitle1;
	}

	public void setHeaderTitle2(String headerTitle2) {
		this.headerTitle2 = headerTitle2;
	}

	public String getHeaderTitle2() {
		return headerTitle2;
	}

	public void setHeaderLogo(String headerLogo) {
		this.headerLogo = headerLogo;
	}

	public String getHeaderLogo() {
		return headerLogo;
	}

	
	public void setFooterTitle(String footerTitle) {
		this.footerTitle = footerTitle;
	}

	public String getFooterTitle() {
		return footerTitle;
	}

	public void setFooterLinkOrganizationName(String footerLinkOrganizationName) {
		this.footerLinkOrganizationName = footerLinkOrganizationName;
	}

	public String getFooterLinkOrganizationName() {
		return footerLinkOrganizationName;
	}

	public void setFooterOrganizationURL(String footerOrganizationURL) {
		this.footerOrganizationURL = footerOrganizationURL;
	}

	public String getFooterOrganizationURL() {
		return footerOrganizationURL;
	}

	public void setFooterLabelCopyRight(String footerLabelCopyRight) {
		this.footerLabelCopyRight = footerLabelCopyRight;
	}

	public String getFooterLabelCopyRight() {
		return footerLabelCopyRight;
	}

	public void setHelpLink(String helpLink)
	{
		this.helpLink = helpLink;
	}

	public String getHelpLink()
	{
		return helpLink;
	}

	
}
