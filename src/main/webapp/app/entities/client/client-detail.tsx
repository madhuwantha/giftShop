import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './client.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClientDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClientDetail = (props: IClientDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { clientEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clientDetailsHeading">Client</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{clientEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{clientEntity.name}</dd>
          <dt>
            <span id="addressLineOne">Address Line One</span>
          </dt>
          <dd>{clientEntity.addressLineOne}</dd>
          <dt>
            <span id="addressLineTwo">Address Line Two</span>
          </dt>
          <dd>{clientEntity.addressLineTwo}</dd>
          <dt>
            <span id="city">City</span>
          </dt>
          <dd>{clientEntity.city}</dd>
          <dt>
            <span id="postalCode">Postal Code</span>
          </dt>
          <dd>{clientEntity.postalCode}</dd>
          <dt>User</dt>
          <dd>{clientEntity.user ? clientEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/client" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client/${clientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ client }: IRootState) => ({
  clientEntity: client.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClientDetail);
