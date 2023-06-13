
import jwt from "jsonwebtoken";
import { Chat } from '../models/Chat.js';
import { User } from '../models/User.js';
import { createUserMessage } from '../services/Message.js';

// POST - api/Chats/:id/Messages
// done
const CreateMessage = async (req, res) => {
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

      const sender = await User.findOne({ username: data });

      const content = req.body.msg;

      //const message = new Message({ id, created, sender, content });
      const message = await createUserMessage(id, sender, content);

      // Send a success response
      res.status(200).json(message);

    } catch (error) {
      res.status(500).send("unauthorized request");
    }
  }
};


  // GET - api/Chats/:id/Messages
  // done
  const GetMessage = async (req, res) => {
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

        // Find current chat by chat ID
        const currentChat = await Chat.findOne({ id: id }).populate({
          path: 'messages',
          populate: {
            path: 'sender',
            model: 'User'
          }
        });

        // Get the messages from the current chat
        const messages = currentChat.messages;

      // Send the messages as a response
      res.status(200).json(messages);

    } catch (error) {
      console.error(error);
      res.status(500).json({ message: "unauthorized request" });
    }
  }
};


  export { CreateMessage, GetMessage };