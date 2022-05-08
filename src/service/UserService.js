const User = require('../model/User')


export const createUser = await User.create(user);

export const deleteUser = await User.destroy({where: {id: user.id}})

export const updateUser = await User.update(user, {
    where: {
        id: user.id
    }
})
export const findUser = await User.findByPk(user.id);


