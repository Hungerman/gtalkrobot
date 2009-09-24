package com.dictone.servlet;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.guestbook.PMF;

import com.dictone.model.Word;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


@SuppressWarnings("serial")
public class AddWordServlet extends HttpServlet {
       public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
      
        String word = req.getParameter("word");
        String pronounce = req.getParameter("pronounce");
        String pronounceType = req.getParameter("pronounceType");
        String wordType = req.getParameter("wordType");
        String meaning = req.getParameter("meaning");
        String sentence = req.getParameter("sentence");
        String comments = req.getParameter("comments");
        
        Word wordObj = new Word( user,  word,  pronounce,
                pronounceType,  wordType,  meaning,
             sentence,  comments);

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(wordObj);
        } finally {
            pm.close();
        }
        
        resp.sendRedirect("/wordlist.jsp");
    }
}