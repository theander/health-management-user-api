const express = require('express');
const router = express.Router();
const User = require("../service/UserService");


router.get('/login', (req, res) => {
    User.create({
        "name": "Anderson",
        "password": "123456",
        "accessType": "master"
    })


    res.send({
        "name": "Anderson",
        "password": "123456",
        "accessType": "master"
    })


})


router.get('/crypt', (req, res) => {





    res.send({
        "name": "Anderson",
        "password": "123456",
        "accessType": "master"
    })


})


module.exports = router;