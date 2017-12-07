<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tns="http://www.verkkokauppa.com">

    <xsl:template match="/">
        <html>
            <body>
                <h2>Guns</h2>
                <table>
                    <tr bgcolor="#629be0">
                        <th>Model</th>
                        <th>Handy</th>
                        <th>Origin</th>
                    </tr>
                    <xsl:for-each select="tns:guns/gun">
                        <tr>
                            <td><xsl:value-of select="model"/></td>
                            <td><xsl:value-of select="handy"/></td>
                            <td><xsl:value-of select="origin"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>