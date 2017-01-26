/*
 * Accio is a program whose purpose is to study location privacy.
 * Copyright (C) 2016-2017 Vincent Primault <vincent.primault@liris.cnrs.fr>
 *
 * Accio is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Accio is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Accio.  If not, see <http://www.gnu.org/licenses/>.
 */

import React from "react";
import {Panel} from "react-bootstrap";
import Spinner from "react-spinkit";
import RunTable from "../../run/list/RunTable";

let LastRunsPanel = React.createClass({
  render: function () {
    return (
      <Panel header="Last runs"
             className="accio-view-panel"
             collapsible={true}
             defaultExpanded={true}>

          {(null != this.props.runs)
            ? (this.props.runs.length > 0) ? <RunTable runs={this.props.runs} showWorkflow={false}/> : <p>This workflow has never been launched.</p>
            : <Spinner spinnerName="three-bounce"/>}
      </Panel>
    );
  }
});

LastRunsPanel.propTypes = {
    runs: React.PropTypes.array,
};

export default LastRunsPanel;