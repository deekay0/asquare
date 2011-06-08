<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<html>
			<body>
				<h2>Exported Requirements</h2>
				<table border="1">
					<tr bgcolor="#C3D9FF">
						<th>Requirement Id</th>
						<th>Title</th>
						<th>Description</th>
						<th>Priority</th>
						<th>Project Id</th>
						<th>Type</th>
					</tr>
					<xsl:for-each select="Square/RequirementElements/Requirement">
					<xsl:sort select="priority"/>
						<tr>
							<td>
								<xsl:value-of select="id" />
							</td>
							<td>
								<xsl:value-of select="title" />
							</td>
							<td>
								<xsl:value-of select="description" />
							</td>
							<td>
								<xsl:value-of select="priority" />
							</td>
							<td>
								<xsl:value-of select="project" />
							</td>
							
							<td>
							   <xsl:choose>
							      <xsl:when test="security = true and privacy = false">
							      Security
							      </xsl:when>
							      <xsl:when test="security = 'false' and privacy = 'true'">
							      Privacy
							      </xsl:when>
							      <xsl:when test="security = 'true' and privacy = 'true'">
							      Security and Privacy
							      </xsl:when>
							      <xsl:otherwise>
							      Unknown Category
							      </xsl:otherwise>
							   </xsl:choose>
							</td>
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>