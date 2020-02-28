import React from 'react';
import { useHistory } from 'react-router-dom';
import { Label, Input, Menu, Dropdown, Container } from 'semantic-ui-react';

const SideNav = props => {
  const history = useHistory();

  const navigateTo = path => {
    history.push(path);
  };

  return (
    <>
      <Menu size="small" vertical borderless>
        <Menu.Item name="inbox">
          <Label color="teal">1</Label>
          Inbox
        </Menu.Item>

        <Menu.Item name="spam">
          <Label>51</Label>
          Spam
        </Menu.Item>

        <Menu.Item name="updates">
          <Label>1</Label>
          Updates
        </Menu.Item>
        <Menu.Item>
          <Input icon="search" placeholder="Search mail..." />
        </Menu.Item>
      </Menu>
    </>
  );
};

export default SideNav;
