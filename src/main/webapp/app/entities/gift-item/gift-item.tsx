import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './gift-item.reducer';
import { IGiftItem } from 'app/shared/model/gift-item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGiftItemProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const GiftItem = (props: IGiftItemProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { giftItemList, match, loading } = props;
  return (
    <div>
      <h2 id="gift-item-heading" data-cy="GiftItemHeading">
        Gift Items
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Gift Item
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {giftItemList && giftItemList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>No</th>
                <th>Gift Name</th>
                <th>Description</th>
                <th>Unit Price</th>
                <th>Available Quantity</th>
                <th>Category</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {giftItemList.map((giftItem, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${giftItem.id}`} color="link" size="sm">
                      {giftItem.id}
                    </Button>
                  </td>
                  {/*<td>{giftItem.id}</td>*/}
                  <td>{giftItem.giftName}</td>
                  <td>{giftItem.descripption}</td>
                  <td>{giftItem.unitPrice}</td>
                  <td>{giftItem.avalibleQuantity}</td>
                  <td>{giftItem.category ? <Link to={`category/${giftItem.category.id}`}>{giftItem.category.categoryName}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${giftItem.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${giftItem.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${giftItem.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Gift Items found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ giftItem }: IRootState) => ({
  giftItemList: giftItem.entities,
  loading: giftItem.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GiftItem);
