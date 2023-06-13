import express from 'express';
import { GetUserDetails } from '../controllers/User.js';

const router = express.Router();
router.get('/:username', GetUserDetails);

export default router;