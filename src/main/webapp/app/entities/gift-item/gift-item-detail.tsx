import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './gift-item.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGiftItemDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GiftItemDetail = (props: IGiftItemDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { giftItemEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="giftItemDetailsHeading">GiftItem</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{giftItemEntity.id}</dd>
          <dt>
            <span id="giftName">Gift Name</span>
          </dt>
          <dd>{giftItemEntity.giftName}</dd>
          <dt>
            <span id="descripption">Descripption</span>
          </dt>
          <dd>{giftItemEntity.descripption}</dd>
          <dt>
            <span id="unitPrice">Unit Price</span>
          </dt>
          <dd>{giftItemEntity.unitPrice}</dd>
          <dt>
            <span id="avalibleQuantity">Avalible Quantity</span>
          </dt>
          <dd>{giftItemEntity.avalibleQuantity}</dd>
          <dt>Image</dt>
          <dd>{giftItemEntity.image ? giftItemEntity.image.id : ''}</dd>
          <dt>Category</dt>
          <dd>{giftItemEntity.category ? giftItemEntity.category.id : ''}</dd>
          <img
            src={giftItemEntity.image != undefined ? 'http://localhost:8080/public/image/' + giftItemEntity.image.imagepath : ''}
            alt=""
          />
        </dl>
        <Button tag={Link} to="/gift-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gift-item/${giftItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ giftItem }: IRootState) => ({
  giftItemEntity: giftItem.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GiftItemDetail);
