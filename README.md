# SOCIALGIFT

The development of an applica1on to manage "wishlists" is proposed, allowing users to create their
own wish list, as well as view those of their friends and thus be able to reserve products to give them
as gifts without them knowing, while maintaining the anonymity of the gift givers.
This would be applicable to various types of events, such as birthdays, wedding registries, or Christmas
gifts. In this way, a user could create mul1ple lists depending on the event in which they want to receive
each gift.

Since ini1ally the database of products would be empty, each 1me a user wants to add a product to
one of their lists, they would have to check if it already exists and, if not, create it from scratch. A
product would consist of certain characteristics: a photograph, a descriptive name, the price, the brand
and/or model. However, if the product can be purchased online, it could be accompanied by a link to
the purchase page or, in the case of marketplaces like Amazon. This would ensure that the user is given
exactly the product they desire.

As for the graphical interface, it would be similar to that of other social networks. It would consist of a
feed on the main page, where informa1on about the different actions performed by the user's friends
would appear (when someone creates a new list, adds a product, etc.), as well as a user profile where
all their lists and each of the gifts they contain are displayed, and from where they can be reserved.
The lists could be sorted by criteria such as price or the priority given to each product.
In case the deadline arrives and a gift has not been reserved, that is, the recipient would be left without
it, there would be the possibility of moving it to another list for a future event.

## Main functionalities

### User Management

- [x] User register
- [x] Login
- [ ] Login with token
- [ ] Logout
- [ ] View profile
- [ ] Edit profile

### Interaction with users (social)

- [ ] Search for user by email
- [ ] View user profile
- [ ] Send friend request
- [ ] Accept or reject friend request
- [ ] Get all wishlists of user
- [ ] Get all gifts reserverd by user

### Gift list and gift management

- [ ] Create gift list
- [ ] Edit gift list
- [ ] Delete gift list
- [ ] Create gifts
- [ ] Edit gifts
- [ ] Delete gifts
- [ ] View gifts
- [ ] View user who reserved gift

### Messanging with users (OPTIONAL)

## Doc of APIs

- SocialGift API: https://balandrau.salle.url.edu/i3/socialgiE/api-docs/v1/
- MercadoExpress: https://balandrau.salle.url.edu/i3/mercadoexpress/api-docs/v1/
- MercadoExpress(repo images): https://balandrau.salle.url.edu/i3/repositoryimages
