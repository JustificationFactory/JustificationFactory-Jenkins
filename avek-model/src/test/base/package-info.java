/**
 * 
 */
/**
 * @author bgrabiec
 *
 */
@XmlJavaTypeAdapter(value= ADateAdapter.class,type=GregorianCalendar.class)
package fr.axonic.base;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.GregorianCalendar;