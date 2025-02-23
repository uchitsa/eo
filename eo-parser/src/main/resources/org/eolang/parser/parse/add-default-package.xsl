<?xml version="1.0" encoding="UTF-8"?>
<!--
The MIT License (MIT)

Copyright (c) 2016-2025 Objectionary.com

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" id="add-default-package" version="2.0">
  <!--
  Here we go through all objects that are not:
    1. methods (starts with .)
    2. @, Q, ^ or $
    3. mentioned in aliases

  and add default package to them.

  We ignore objects that are present in aliases with their exact
  names. For example, this object 'hello' won't be touched, we
  won't think that it belongs to org.eolang package:

  +alias hello

  # No comment.
  [] > app
    hello > @
  -->
  <xsl:output encoding="UTF-8" method="xml"/>
  <xsl:template match="o[@base]">
    <xsl:apply-templates select="." mode="with-base"/>
  </xsl:template>
  <xsl:template match="o[starts-with(@base, 'org.eolang')]" mode="with-base">
    <xsl:copy>
      <xsl:attribute name="base">
        <xsl:text>Q.</xsl:text>
        <xsl:value-of select="@base"/>
      </xsl:attribute>
      <xsl:apply-templates select="node()|@* except @base"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="o[not(contains(@base, '.'))]" mode="with-base">
    <xsl:apply-templates select="." mode="no-dots"/>
  </xsl:template>
  <xsl:template match="o[@base!='@' and @base!='Q' and @base!='^' and @base!='∅' and @base!='$']" mode="no-dots">
    <xsl:apply-templates select="." mode="no-specials"/>
  </xsl:template>
  <xsl:template match="o[not(@base=/program/metas/meta[head='alias']/part[1])]" mode="no-specials">
    <xsl:copy>
      <xsl:attribute name="base">
        <xsl:text>Q.org.eolang.</xsl:text>
        <xsl:value-of select="@base"/>
      </xsl:attribute>
      <xsl:apply-templates select="node()|@* except @base"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="node()|@*" mode="#all">
    <xsl:copy>
      <xsl:apply-templates select="node()|@*"/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>
