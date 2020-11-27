'use strict';

DEBUG: true

const createApplication = require('./');
const { AuthorizationCode } = require('./../');

createApplication(({ app, callbackUrl }) => {
  const client = new AuthorizationCode({
    client: {
      id: process.env.CLIENT_ID,
      secret: process.env.CLIENT_SECRET,
    },
    auth: {
      tokenHost: 'https://qa.awacscloud.tech',
      tokenPath: '/authserver/oauth/token',
      authorizePath: '/authserver/oauth/authorize',
    },
  });

  // Authorization uri definition
  const authorizationUri = client.authorizeURL({
    redirect_uri: callbackUrl,
    scope: 'read write',
    state: '3(#0/!~',
  });

  // Initial page redirecting to Github
  app.get('/authorize', (req, res) => {
    console.log(authorizationUri);
    res.redirect(authorizationUri);
  });

  // Callback service parsing the authorization token and asking for the access token
  app.get('/login', async (req, res) => {
    const options = { 
      grant_type: 'password',
      username: 'admin',
      password: 'admin1234'
   };

    try {
      const accessToken = await client.getToken(options);

      console.log('The resulting token: ', accessToken.token);

      return res.status(200).json(accessToken.token);
    } catch (error) {
      console.error('Access Token Error', error.message);
      return res.status(500).json('Authentication failed');
    }
  });

  app.get('/', (req, res) => {
    res.send('Hello<br><a href="/login">Log in with Awacs</a>');
  });
});
