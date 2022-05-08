const express = require('express');
const router = express.Router();


router.get('/login', (req, res) => {
    res.send({"name":"User",
    "Password":"pass",
    "accessType":"S_Admin"})

})

module.exports=router;