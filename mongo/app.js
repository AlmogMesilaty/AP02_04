import express from 'express'
const app = express();

import bodyParser from 'body-parser'
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.json());

import cors from 'cors'
app.use(cors());

import customEnv from 'custom-env'
customEnv.env(process.env.NODE_ENV, './config');

import mongoose from 'mongoose'
console.log(process.env.CONNECTION_STRING);
console.log(process.env.port)
mongoose.connect(process.env.CONNECTION_STRING, {
    useNewUrlParser: true,
    useUnifiedTopology: true
});

app.use(express.static('public'));

import Chat from './routes/Chat.js';
app.use('/api/Chats', Chat);

import UserPass from './routes/UserPass.js';
app.use('/api/Tokens', UserPass);

import UserPassName from './routes/UserPassName.js';
app.use('/api/Users', UserPassName);

import User from './routes/User.js';
app.use('/api/Users', User);

// part 3
import path from 'path'
app.get('/socket.io/socket.io.js', (req, res) => {
    res.sendFile(path.resolve('node_modules', 'socket.io', 'client-dist', 'socket.io.js'));
});

import http from 'http'
const server = http.createServer(app);

import { Server } from 'socket.io'
const io = new Server(server, {
    cors: {
        origin: "http://localhost:3000",
        methods: ["GET", "POST"],
    },
});

import { Chat as ChatModel } from './models/Chat.js'

io.on('connection', (socket) => {

    // Event handler for 'sentMessage' event
    socket.on('sentMessage', async (data) => {
      const username = data.sender;
      const id = data.chatId;
      const chat = await ChatModel.findOne({ id: id }).populate('users');
      const users = chat.users;
      let target;
      let sender;
      if (users[0].username === username) {
        target = users[1];
        sender = users[0];
      } else {
        target = users[0];
        sender = users[1];
      }

      socket.broadcast.emit('receivedMessage', { sender: sender, receiver: target });

    });
  });


server.listen(process.env.PORT);



