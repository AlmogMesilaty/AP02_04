import express from 'express';
import {GetChats, CreateChats, GetChat} from '../controllers/Chat.js';
import { CreateMessage, GetMessage } from '../controllers/Message.js'

const router = express.Router();

router.get('/:id', GetChat);
router.get('/', GetChats);
router.post('/', CreateChats);
router.delete('/:id', GetChat);

router.post('/:id/Messages', CreateMessage);
router.get('/:id/Messages', GetMessage);

export default router;