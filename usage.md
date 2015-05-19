# End-user's how-to #
It is very easy for end-user to use the GTRobot.

## Requirement ##
  1. You should own a google account (gmail account). If you don't have one, please go to _**[Create a Google Account](https://www.google.com/accounts/NewAccount?service=ig&passive=true&continue=http://www.google.com/ig%3Fhl%3Den-Us&followup=http://www.google.com/ig%3Fhl%3Den-Us&cd=US&hl=en&nui=1&ltmpl=default)**_.
  1. It is better choice to install GTalk. If you don't have gtalk installed, please go to _**[Download and install GTalk](http://www.google.com/talk/index.html)**_. Otherwise, you can use the gamil online chatting interface to use the GTRobot.
  1. Add the GTRobot account to your contacts in GTalk or Gmail.
  1. Talking with GTRobot.

## How to find a GTRobot account ##
We provide the GTRobot program for some guys who would like to set up the GTRobot. Once one GTRobot was set up, we will publish the New GTRobot's Account.
In the future, we plan to maintenance a active GTRobot list for you use.

Till now, we can provide one test GTRobot account _(robot.gtalk.cn)_ for your experience the GTRobot's function. And we are very sorry that we can not promise to keep this account active at all time.

If you are interesting in the GTRobot, and would like to setup one, please post in [GTRobot's Group](http://groups.google.com/group/GTRobot)  or [GTRobot's blog](http://gtrobot.blogspot.com/). Thanks.

## Commands ##
GTRobot provids two command style, global command style and interactive command style.
All commands are case ingored.
### Global Commands ###
GTRobot uses the head character '/' or ':' to indentify Global commands. '/' was also used by the _**[Google Talk: Conference Bot](http://coders.meta.net.nz/~perry/jabber/confbot.php)**_, it is truely convenient. The reason using ':' is the '/' does not work in some jabbar client, like Gaim. Maybe some guys will use them to access GTRobot.

Global command list:
  1. Help
    * Command format: /help
    * Show the GTRobot's help information, including global commands list.
  1. Away
    * Command format: /nochat or /away
    * Set your status to away, you will not recieve any group chatting message. But you can continue interact with GTRobot using the commands, also if some one send you a private message, you will recieve it immediately.
  1. Available
    * Command format: /chat or /available
    * Set your status back to group chatting. You will recieve group chatting messages if you are in public chatting group or private chatting group.
  1. Echo
    * Command format: /echo on|off
    * Set the echo on or off. If you set echo on, your chatting message will be send back to yourself. Default is echo off.
    * Sample:
```
        #> /echo on
        #> /echo off
```
  1. Language setting
    * Command format: /lang en|zh|jp
    * Set your interface language setting. Now, it supports English, Chinese, Japanese language interfaces.
    * Sample:
```
        #> /lang en
        #> /lang zh
        #> /lang jp
```
  1. Search online user
    * Command format: /sc _matchString_
    * Search the online user. If the matchString is blank, all online user will be returned. Otherwise all users will be returned whose name includes the matchString. The user list will be returned in paging. Each page only shows 10 users. Please use command "//" or "::" to request the following pages.
    * Sample:
```
        #> /sc          : Return all users
        #> /sc boy      : Return all users whose name includes "boy"
```
  1. Show your status
    * Command format: /st or /status
    * Show yourself status information.
    * Sample:
```
        #> /st
        #> /status
```
  1. Send a private message
    * Command format: /pm emailId message  or  /privatemessage emailId message
    * Send a private message to one user with gmail id. The _emailId_ should be the reciever's google account(Gmail Account). The message can be a sentence or paragraph.
    * Sample:
```
        #> /pm robot.gtalk.cn  Hey, how are you, GTRobot?
        #> /privatemessage reshine  Hey, how the development of GTRobot is going?
```
  1. Repeat command
    * Command format: // or ::
    * Repeat the previous command. It is often used for some repeatable command, like _**"Search online user"**_.

  1. **Broadcast message**
    * Command format: message
    * When you are in public chatting group or one of private chatting group, any message without '/' or ':' headed will be broadcasted to the chatting group.

#### Public chatting group & Private chatting group ####
When your status is _**"available"**_, you are not in any private chatting group, and you are not in any interactive operation, then you are in the public chatting group. Public chatting group is the default conference room for all users in GTRobot.

Private chatting group is under development till now.

### Interactive Commands ###
Under development.



# How to set up GTRobot #
Under development.


If you are interesting in seting up one GTRobot, please post in [GTRobot's Group](http://groups.google.com/group/GTRobot)  or [GTRobot's blog](http://gtrobot.blogspot.com/). We are pleasing to provide you some internal draft documents about setup.
Thanks.
