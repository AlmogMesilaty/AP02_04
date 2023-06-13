
import jwt from "jsonwebtoken";
import { Chat } from '../models/Chat.js';
import { User } from '../models/User.js'
import { createUserChat } from '../services/Chat.js'

//GET - api/Chats/:id
// done
const GetChat = async (req, res) => {

  // If the request has an authorization header
  if (req.headers.authorization) {
    // Extract the token from that header
    const tokenRaw = req.headers.authorization.split(" ")[1];
    const tokenJson = JSON.parse(tokenRaw);
    const token = tokenJson.token;
    try {

      // verify the token is valid
      const data = jwt.verify(token, "secret");

      const id = req.params.id;

      // Find the chat by ID
      const chat = await Chat.findOne({ id: id });

      // Send the chat as a response
      res.status(200).json(chat);

    } catch (error) {
      res.status(500).json({ message: "unauthorized request " });
    }
  }
};

//GET - api/Chats
// done
const GetChats = async (req, res) => {
  // If the request has an authorization header
  if (req.headers.authorization) {
    // Extract the token from that header
    const tokenRaw = req.headers.authorization.split(" ")[1];
    const tokenJson = JSON.parse(tokenRaw);
    const token = tokenJson.token;

    try {
      // verify the token is valid
      const data = jwt.verify(token, "secret");

      // Find all chats where current logged user is a participant
      const chats = await Chat.find().populate('users').populate({
        path: 'messages',
        populate: {
        path: 'sender',
        model: 'User'
      }}).exec();


      const userChats = chats.filter(chat => {
        // Check if username is a participant in the chat
        return chat.users.some(user => user.username === data);
      });

      const smallUserChats = [];

      userChats.forEach(chat => {
        // get chat id
        var id = chat.id;
        // get user that is not the logged user
        var user;
        if (chat.users[0].username === data) {
          user = chat.users[1];
        } else {
          user = chat.users[0];
        }
        // get the last message
        if (chat.messages.length > 0) {
          var lastMessage = chat.messages[0];
        } else {
          var lastMessage = "Welcome!"
        }

        // insert redundent chat to array
        smallUserChats.push({ id: id, user: user, lastMessage: lastMessage });
      });

      // Send the chats as a response
      res.status(200).json(smallUserChats);

    } catch (error) {
      console.error(error);
      res.status(500).json({ message: "unauthorized request" });
    }
  }
};

//POST - api/Chats
// done
const CreateChats = async (req, res) => {
  // If the request has an authorization header
  if (req.headers.authorization) {
    // Extract the token from that header
    const tokenRaw = req.headers.authorization.split(" ")[1];
    const tokenJson = JSON.parse(tokenRaw);
    const token = tokenJson.token;

    try {
      // verify the token is valid
      const data = jwt.verify(token, "secret");

      // finds user the logged user by username
      const user1 = await User.findOne({ username: data });

      // find the user that we created a chat with, by username
      const user2 = await User.findOne({ username: req.body.username });

      const users = [user1, user2];

      const messages = [];

      // Save the chat to the database
      const chat = await createUserChat(users, messages);

      // Send a success response
      res.status(200).json(chat);

    } catch (error) {
      res.status(500).json({ message: "unauthorized request" });
    }
  }
};

//DELETE - api/Chats/:id
// done
const DeletChat = async (req, res) => {
  // If the request has an authorization header
  if (req.headers.authorization) {
    // Extract the token from that header
    const tokenRaw = req.headers.authorization.split(" ")[1];
    const tokenJson = JSON.parse(tokenRaw);
    const token = tokenJson.token;

    try {
      // verify the token is valid
      const data = jwt.verify(token, "secret");

      const id = req.params.id;

      // Find the chat by ID and delete it
      const result = await Chat.deleteOne({ id: id });

      // Send a success response
      res.status(200).json({ message: 'Chat deleted successfully' });

    } catch (error) {
      // Handle any errors
      res.status(500).json({ message: 'Internal server error' });
    }
  }
};

export { GetChat, GetChats, CreateChats, DeletChat };

