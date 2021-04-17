import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/entity/category">
      Category
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/gift-item">
      Gift Item
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/image">
      Image
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/gift-order">
      Gift Order
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/cart">
      Cart
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/employee">
      Employee
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/client">
      Client
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
