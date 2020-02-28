import React from 'react';
import { useHistory } from 'react-router-dom';
import { Icon, Input, Menu, Dropdown, Container } from 'semantic-ui-react';

const Nav = props => {
  const history = useHistory();

  const navigateTo = path => {
    history.push(path);
  };

  return (
    <>
      <Menu size="large" fixed="top" fluid borderless>
        <Container>
          <Menu.Item onClick={() => navigateTo('/login')}>
            <Icon name="twitter" color="blue" />
            Microblogging
          </Menu.Item>
          {props.children}
          <Menu.Item>
            <Input icon="search" placeholder="Search people, posts, tags..." />
          </Menu.Item>

          <Menu.Menu position="right">
            <Dropdown direction="left" icon="user" floating item>
              <Dropdown.Menu>
                <Dropdown.Item onClick={() => navigateTo('/login')}>
                  User profile
                </Dropdown.Item>
                <Dropdown.Item onClick={() => navigateTo('/login')}>
                  Setting
                </Dropdown.Item>
                <Dropdown.Item onClick={() => navigateTo('/login')}>
                  Logout
                </Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
          </Menu.Menu>
        </Container>
      </Menu>
    </>
  );
};

export default Nav;
