package imp.sentiment;


/**
 * Utilities for String formatting, manipulation, and queries.
 * 
 * @author 
 * 
 */
public class StringHelper {
  /**
   * Replaces characters that may be confused by an SQL
   * parser with their equivalent escape characters.
   * <p>
   * Any data that will be put in an SQL query should
   * be be escaped.  This is especially important for data
   * that comes from untrusted sources such as Internet users.
   * <p>
   * For example if you had the following SQL query:<br>
   * <code>"SELECT * FROM addresses WHERE name='" + name + "' AND private='N'"</code><br>
   * Without this function a user could give <code>" OR 1=1 OR ''='"</code>
   * as their name causing the query to be:<br>
   * <code>"SELECT * FROM addresses WHERE name='' OR 1=1 OR ''='' AND private='N'"</code><br>
   * which will give all addresses, including private ones.<br>
   * Correct usage would be:<br>
   * <code>"SELECT * FROM addresses WHERE name='" + StringHelper.escapeSQL(name) + "' AND private='N'"</code><br>
   * <p>
   * Another way to avoid this problem is to use a PreparedStatement
   * with appropriate placeholders.
   *
   * @param  String to be escaped
   * @return escaped String
   * @throws NullPointerException if s is null.
   *
   * 
   */
  public static String escapeSQL(String s){
    int length = s.length();
    int newLength = length;
    // first check for characters that might
    // be dangerous and calculate a length
    // of the string that has escapes.
    for (int i=0; i<length; i++){
      char c = s.charAt(i);
      switch(c){
        case '\\':
        case '\"':
        case '\'':
        case '\0':{
          newLength += 1;
        } break;
      }
    }
    if (length == newLength){
      // nothing to escape in the string
      return s;
    }
    StringBuffer sb = new StringBuffer(newLength);
    for (int i=0; i<length; i++){
      char c = s.charAt(i);
      switch(c){
        case '\\':{
          sb.append("\\\\");
        } break;
        case '\"':{
          sb.append("\\\"");
        } break;
        case '\'':{
          sb.append("\\\'");
        } break;
        case '\0':{
          sb.append("\\0");
        } break;
        default: {
          sb.append(c);
        }
      }
    }
    return sb.toString();
  }
}

   