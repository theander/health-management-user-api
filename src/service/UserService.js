const User = require('../model/User');
const bcrypt = require("bcryptjs");


async function createUser(user) {
    const salt = "s3nh@"
    bcrypt.genSalt(10, function (err, salt) {
        bcrypt.hash(senha, salt, function (err, hash) {
            user.password = hash;
            return User.create(user);
        });
    });
}

async function checkUser(user) {
    const password = user.password;
    const returnedUser = await User.findOne({
        where: {name: user.username},
    });
    const hash = returnedUser.password;
    bcrypt.compare(password, hash).then((res) => {
        console.log(res)
    });
}

module.exports = {createUser, checkUser}
