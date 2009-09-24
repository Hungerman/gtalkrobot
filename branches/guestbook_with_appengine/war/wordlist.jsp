<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.dictone.model.Word" %>
<%@ page import="org.guestbook.PMF" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>
  <body>

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();

    PersistenceManager pm = PMF.get().getPersistenceManager();
    String query = "select from " + Word.class.getName();
    List<Word> wordList = (List<Word>) pm.newQuery(query).execute();
    if (wordList.isEmpty()) {
%>
<p>There is no word.</p>
<%
    } else {
%>

<table>
<%
        for (Word w : wordList) {
%>
<tr>
<td><%= w.getWord() %> </td>
<td><%= w.getPronounce() %> </td>
<td><%= w.getMeaning() %> </td>
</tr>
<%
        }
%>
</table>
<%
    }
%>
<p>
    <form action="/addWord" method="post">
      <input type="text" name="word" value="" />
      <input type="pronounce" name="pronounce" value="" />
      <div><textarea name="meaning" rows="3" cols="60"></textarea></div>
      <div><input type="submit" value="addWord" /></div>
    </form>
  </body>
</html>