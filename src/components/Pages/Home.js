import React from 'react';
import { useHistory } from 'react-router-dom';

import Nav from '../Nav';
import { Menu } from 'semantic-ui-react';

const styles = {
  color: 'blue',
  buttonLink: {
    color: 'blue',
    background: 'none',
    padding: 0,
    fontWeight: 100
  }
};

const Home = () => {
  const history = useHistory();

  const navigateTo = path => {
    history.push(path);
  };

  const handleSubmit = e => {
    e.preventDefault();
  };

  return (
    <>
      <Nav>
        <Menu.Item name="home" onClick={() => navigateTo('/login')} />
        <Menu.Item name="messages" onClick={() => navigateTo('/login')} />
        <Menu.Item name="friends" onClick={() => navigateTo('/login')} />
      </Nav>
    </>
  );
};

export default Home;
