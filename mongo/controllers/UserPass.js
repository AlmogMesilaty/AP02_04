
import { UserPass } from '../models/UserPass.js';
import { CreateToken } from '../services/Token.js';

//POST - /api/tokens
// done
const createToken = async (req, res) => {

    const { username, password } = req.body;

    const user = await UserPass.findOne({ username: username })

    if (user && user.password === password) {
      const token = await CreateToken(username);
      // Send the token
      res.status(200).json(token);

    } else {
      res.status(500).send(null);
    }
};

export { createToken };