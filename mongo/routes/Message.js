import express from 'express';
import {CreateMessage, GetMessage} from '../controllers/Message.js';

const router = express.Router();
router.post('/', CreateMessage);
router.get('/', GetMessage);

export default router;