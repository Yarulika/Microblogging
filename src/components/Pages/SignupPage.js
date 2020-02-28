import React from 'react';
import { useHistory } from 'react-router-dom';
import {
  Button,
  Form,
  Grid,
  Header,
  Message,
  Segment,
  Icon
} from 'semantic-ui-react';

const styles = {
  color: 'blue',

  buttonLink: {
    color: 'blue',
    background: 'none',
    padding: 0,
    fontWeight: 100
  }
};

const SignupPage = () => {
  const history = useHistory();

  const navigateTo = path => {
    history.push(path);
  };

  const handleSubmit = e => {
    e.preventDefault();
  };

  return (
    <Grid textAlign="center" style={{ height: '100vh' }} verticalAlign="middle">
      <Grid.Column style={{ maxWidth: 500 }}>
        <Header color={styles.color} content="Microblogging" as="h1" />
        <Header color={styles.color} icon>
          <Icon name="twitter" />
          <Header.Content>
            Sign Up
            <Header.Subheader>Way to Microblogging</Header.Subheader>
          </Header.Content>
        </Header>

        <Form size="mini" onSubmit={handleSubmit}>
          <Segment basic>
            <Form.Input
              fluid
              icon="user"
              iconPosition="left"
              placeholder="Your username"
              required
            />
            <Form.Input
              fluid
              icon="at"
              iconPosition="left"
              placeholder="E-mail address"
              required
            />
            <Form.Input
              fluid
              icon="lock"
              iconPosition="left"
              placeholder="Password"
              type="password"
              required
            />

            <Button color={styles.color} fluid size="large" type="submit">
              Sign Up
            </Button>

            <Message>
              Already registered?{' '}
              <Button
                style={styles.buttonLink}
                onClick={() => navigateTo('/login')}
              >
                Log In
              </Button>
            </Message>
          </Segment>
        </Form>
      </Grid.Column>
    </Grid>
  );
};

export default SignupPage;
