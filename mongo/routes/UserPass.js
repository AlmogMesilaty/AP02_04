import express from 'express';
import { createToken, createFcmToken } from '../controllers/UserPass.js';


const router = express.Router();

router.post('/', createToken);

router.post('/FCM', createFcmToken);

export default router;