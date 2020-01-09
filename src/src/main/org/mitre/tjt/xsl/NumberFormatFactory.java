/*
 * File: NumberFormatFactory.java
 *
 * $Id: NumberFormatFactory.java 3633 2003-03-01 07:38:44Z kvisco $
 */

package org.mitre.tjt.xsl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.mitre.tjt.text.AlphaNumberFormat;
import org.mitre.tjt.text.RomanNumberFormat;

public class NumberFormatFactory {
  public NumberFormatFactory() {
  		//{{INIT_CONTROLS
		//}}
}

  public NumberFormat getFormat(char firstFormatChar) {
    switch(firstFormatChar) {
    case 'A' :
    case 'a' :
      return new AlphaNumberFormat((firstFormatChar == 'A'));

    case 'I' :
    case 'i' :
      return new RomanNumberFormat((firstFormatChar == 'I'));

    case '1' :
    default :
      return new DecimalFormat("######");
    }
  }
	//{{DECLARE_CONTROLS
	//}}
}
